(defproject scramble "0.1.0-SNAPSHOT"

  :description "Scramblies challenge"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.879"]
                 [ring "1.9.4"]
                 [compojure "1.6.2"]
                 [environ "1.2.0"]
                 [mount "0.1.16"]

                 [thheller/shadow-cljs "2.15.10"]
                 [reagent "1.1.0"]
                 [re-frame "1.2.0"]
                 [cljs-ajax "0.8.3"]
                 [binaryage/devtools "1.0.3"]]

  :min-lein-version "2.9.0"

  :jvm-opts ["-server"]
  :source-paths ["src" "cljs"]
  :resource-paths ["resources"]

  :plugins [[lein-environ "1.1.0"]
            [lein-midje "3.2.1"]]

  :main ^:skip-aot scramble.core


  :profiles {:uberjar {:omit-source  true
                       :aot          :all
                       :prep-tasks ["compile"
                                    ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]]
                       :uberjar-name "scramble.jar"}
             :dev     {:source-paths ["test"]
                       :repl-options {:init-ns scramble.core}
                       :env          {:http-port "9090"}}
             :midje   {:dependencies [[midje "1.9.9"]
                                      [clj-http "3.12.3"]]
                       :source-paths ["test"]
                       :env          {:http-port "9191"}}})
