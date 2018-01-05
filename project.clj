(defproject democracyworks/clj-smartystreets "0.2.0"
  :description "A Clojure library wrapping SmartyStreets' Cloud APIs."
  :url "https://github.com/democracyworks/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.7.0"]
                 [cheshire "5.8.0"]]
  :profiles {:dev {:dependencies [[clj-http-fake "1.0.3"]
                                  [midje "1.9.1"]]
                   :repl-options {:init-ns dev.user}
                   :source-paths ["dev-src"]}}
  :plugins [[lein-midje "3.2.1"]]
  :aliases {"test" ["do" ["with-profile" "+test" "midje"]
                         ["test"]]})
