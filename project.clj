(defproject scramble "0.1.0-SNAPSHOT"

  :description "Scramblies challenge"

  :dependencies [[org.clojure/clojure "1.10.1"]]

  :min-lein-version "2.0.0"
  :source-paths ["src"]
  :resource-paths ["resources"]

  :main ^:skip-aot scramble.core


  :profiles {:uberjar {:omit-source  true
                       :aot          :all
                       :uberjar-name "scramble.jar"}
             :dev {:source-paths ["repl"]
                   :repl-options {:init-ns scramble.core}}})
