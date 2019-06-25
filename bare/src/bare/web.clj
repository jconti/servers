(ns bare.web
  (:require
   [bare.logic :as logic]
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]))

(require '[clojure.pprint])

(defmethod ig/init-key :adapter/jetty [_ {:keys [port db] :as cfg}]
  (let [handler (logic/make-handler db)]
    {:handler handler
     :server (jetty/run-jetty handler (assoc cfg :join? false))}))

(defmethod ig/halt-key! :adapter/jetty [_ {:keys [server]}]
  (.stop server)
  nil)
