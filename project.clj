(defproject democracyworks/clj-smartystreets "1.0.0"
  :description "A Clojure library wrapping SmartyStreets' Cloud APIs."
  :url "https://github.com/democracyworks/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-http "3.12.1"]
                 [cheshire "5.10.0"]]
  :profiles {:dev {:dependencies [[clj-http-fake "1.0.3"]]
                   :repl-options {:init-ns dev.user}
                   :source-paths ["dev-src"]}})
