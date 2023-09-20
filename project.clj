(defproject democracyworks/clj-smartystreets "1.0.1-SNAPSHOT"
  :description "A Clojure library wrapping SmartyStreets' Cloud APIs."
  :url "https://github.com/democracyworks/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [cheshire "5.12.0"]]
  :profiles {:dev {:dependencies [[clj-http-fake "1.0.4"]]
                   :repl-options {:init-ns dev.user}
                   :source-paths ["dev-src"]}}
  :deploy-repositories [["clojars"
                         {:url "https://repo.clojars.org/"
                          :sign-releases false}]])
