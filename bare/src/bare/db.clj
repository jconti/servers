(ns bare.db
  (:require
   [clojure.set :as set]
   [integrant.core :as ig]
   [next.jdbc :as jdbc]))

(defmethod ig/init-key :database/custody [_ {:keys [host dbname] :as cfg}]
  (-> cfg
      (assoc :dbtype "sqlserver" :integratedSecurity true)
      (jdbc/get-connection)))

(defmethod ig/halt-key! :database/custody [_ connection]
  (.close connection))
