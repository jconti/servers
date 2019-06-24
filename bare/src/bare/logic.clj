(ns bare.logic
  (:require
   [next.jdbc :as jdbc]))

(defn complex-proprietary
  [{:keys [connection] :as db}]
  (when (jdbc/execute-one! connection ["select 1"])
    "Woot! The db is there..."))

(defn make-handler
  [db]
  (fn logic-handler [request]
    (when-let [profound-insight (complex-proprietary db)]
      {:status 200 :body profound-insight})))
  
