(ns scramble.core
  (:gen-class)
  (:require
    [mount.core :as mount]
    ;; mount components
    [scramble.server :as http-server]))


(defn destroy
  []
  (prn "System is shutting down...")
  (doseq [component (:stopped (mount/stop))]
    (prn (str component " stopped")))
  (prn "System shutdown complete. Bye."))


(defn stop-app
  []
  (destroy)
  (shutdown-agents))


(defn init
  []
  (prn "System init...")
  (doseq [component (:started (mount/start))]
    (prn (str component " started"))))


(defn start-app
  []
  (init)
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app)))


(defn -main
  [& _]
  (start-app))


#_(start-app)
