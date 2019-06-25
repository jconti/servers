(ns user
  (:require
   [bare.init :as init]
   [clojure.tools.namespace.repl :refer [refresh]]
   [integrant.core :as ig]))

(def config {:database/custody {:host   "boi-mssql-db4"
                                :dbname "custodydata"}
             :adapter/jetty    {:port 4000
                                :db   (ig/ref :database/custody)}})

(defn go [] (init/init! config))
(defn stop [] (init/shutdown!))
(defn reset []
  (init/shutdown!)
  (refresh :after 'user/go))
