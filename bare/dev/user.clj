(ns user
  (:require
   [bare.init :as init]
   [clojure.tools.namespace.repl :refer [refresh]]))

(def config {:db-host "boi-mssql-db4"
             :db-name "custodydata"})

(defn go [] (init/init! config))
(defn stop [] (init/shutdown!))
(defn reset []
  (init/shutdown!)
  (refresh :after 'user/go))
