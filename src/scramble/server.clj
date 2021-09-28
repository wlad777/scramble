(ns scramble.server
  (:require
    [clojure.stacktrace :refer [print-stack-trace]]
    [compojure.core :refer [GET defroutes]]
    [compojure.route :refer [resources not-found]]
    [environ.core :refer [env]]
    [mount.core :refer [defstate]]
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.util.response :as response]))


(defn scramble?
  "returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false"
  [str1 str2]
  (let [chars-map1 (frequencies (or str1 ""))
        chars-map2 (frequencies (or str2 ""))]
    (every? (fn [[c freq]]
              (<= freq (get chars-map1 c 0)))
            chars-map2)))


(defroutes routes
  (GET "/" [] (response/resource-response "index.html" {:root "public"}))
  (GET "/scramble/:str1/:str2" [str1 str2] (response/response (str (scramble? str1 str2))))
  (resources "/")
  (not-found "<h1>Page not found</h1>"))


(defn- port-number
  [port]
  (or (cond
        (number? port) port
        (string? port) (try (Long/parseLong port) (catch Exception _ nil))
        :else nil)
      9090))


(defn- start-server
  [port]
  (try
    (let [port (port-number port)]
      (prn (str "Start server on port " port))
      (run-jetty routes {:port port :join? false}))
    (catch Exception e
      (prn (print-stack-trace e)))))


(defstate http-server
  :start (start-server (env :http-port))
  :stop (when http-server
          (.stop http-server)))


