(ns bare.logic
  (:require
   [bare.global :as global]
   [next.jdbc :as jdbc]))

(defn complex-proprietary
  []
  (when-let [ds (:db-conn @global/state)]
    (when (jdbc/execute-one! ds ["select 1"])
      "Woot! The db is there...")))

(defn handler
  [request]
  (when-let [profound-insight (complex-proprietary)]
    {:status 200 :body profound-insight}))
  
