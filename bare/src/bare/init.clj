(ns bare.init
  (:require
   [bare.global :as global]
   [bare.logic :as logic]
   [clojure.set :as set]
   [clojure.spec.alpha :as s]
   [next.jdbc :as jdbc]
   [ring.adapter.jetty :as jetty]))

(defn read-config
  "Read program configuration"
  []
  ;; see https://12factor.net/config
  {:db-host (System/getenv "DB_HOST")
   :db-name (System/getenv "DB_NAME")
   :http-port (if-let [port-str (System/getenv "HTTP_PORT")]
                (Long/parseLong port-str)
                4000)})

(defn web-config
  "Make an options map for ring.jetty/run-jetty from a program-config"
  [{:keys [http-port] :as program-config}]
  {:port http-port :join? false})

(defn db-config
  "Make a map param for next.jdbc/get-datasource from a program-config"
  [program-config]
  ;; see (clojure.repl/doc next.jdbc/get-datasource)
  (-> program-config
      (select-keys [:db-host :db-name])
      (set/rename-keys {:db-host :host, :db-name :dbname})
      (assoc :dbtype "sqlserver" :integratedSecurity true)))

(defn init!
  "Transform a program-config into a running system."
  [program-config]
  (when-not @global/state
    (let [state {:db-conn (-> program-config
                              (db-config)
                              (jdbc/get-connection))
                 :jetty-stop-fn (->> program-config
                                     (web-config)
                                     (jetty/run-jetty logic/handler))}]
      (reset! global/state state)))
  nil)

(defn shutdown!
  "Stop a running system."
  []
  (when-let [stop-fn (:jetty-stop-fn @global/state)]
    (stop-fn))
  (reset! global/state nil)
  nil)
