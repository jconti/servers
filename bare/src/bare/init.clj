(ns bare.init
  (:require
   [bare.db :as db]
   [bare.global :as global]
   [bare.web :as web]
   [com.stuartsierra.component :as component]))

(defn read-config
  "Read program configuration"
  []
  ;; see https://12factor.net/config
  {:db-host (System/getenv "DB_HOST")
   :db-name (System/getenv "DB_NAME")
   :http-port (when-let [port-str (System/getenv "HTTP_PORT")]
                (Long/parseLong port-str))})

(defn init!
  "Transform a program-config into a running system."
  [program-config]
  (when-not @global/state
    (let [system (-> (component/system-map
                      :db (db/->Db program-config)
                      :web (web/create program-config))
                     (component/system-using
                      {:web [:db]}))]
      (reset! global/state (component/start system))))
  nil)

(defn shutdown!
  "Stop a running system."
  []
  (when-let [system @global/state]
    (component/stop system)
    (reset! global/state nil))
  nil)
