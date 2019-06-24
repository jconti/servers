(ns bare.db
  (:require
   [clojure.set :as set]
   [com.stuartsierra.component :as component]
   [next.jdbc :as jdbc]))

(defn db-config
  "Make a map param for next.jdbc/get-datasource from a program-config"
  [program-config]
  ;; see (clojure.repl/doc next.jdbc/get-datasource)
  (-> program-config
      (select-keys [:db-host :db-name])
      (set/rename-keys {:db-host :host, :db-name :dbname})
      (assoc :dbtype "sqlserver" :integratedSecurity true)))

(defn initialize
  [{:keys [configuration connection] :as this}]
  ;; https://github.com/stuartsierra/component#idempotence
  (if connection
    this
    (assoc this :connection (jdbc/get-connection (db-config configuration)))))

(defn shutdown
  [{:keys [configuration connection] :as this}]
  ;; https://github.com/stuartsierra/component#idempotence
  (if-not connection
    this
    (dissoc this :connection)))

(defrecord Db [configuration]
  component/Lifecycle
  (start [this]
    (initialize this))
  (stop [this]
    (shutdown this)))
