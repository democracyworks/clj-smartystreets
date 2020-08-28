(defproject democracyworks/clj-smartystreets "0.2.1"
  :description "A Clojure library wrapping SmartyStreets' Cloud APIs."
  :url "https://github.com/democracyworks/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-http "3.10.2"]
                 [cheshire "5.10.0"]]
  :profiles {:dev {:dependencies [[clj-http-fake "1.0.3"]
                                  [midje "1.9.9"]]
                   :repl-options {:init-ns dev.user}
                   :source-paths ["dev-src"]}}
  :plugins [[lein-midje "3.2.2"]]
  :aliases {"test" "midje"})
