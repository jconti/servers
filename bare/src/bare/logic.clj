(ns bare.logic
  (:require
   [next.jdbc :as jdbc]
   [taoensso.timbre :as log]))

(defn complex-proprietary
  [connection]
  (try
   (when (jdbc/execute-one! connection ["select 1"])
     {:status 200 :body "Woot! The db is there..."})
   (catch Throwable t
     (let [err-msg (format "Request failed: %s" (.getMessage t))]
       (log/errorf err-msg)
       {:status 500 :body err-msg}))))

(defn make-handler
  [db]
  (fn logic-handler [request]
    (complex-proprietary db)))
  
