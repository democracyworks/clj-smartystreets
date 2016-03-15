(defproject clj-smartystreets "0.1.4"
  :description "A Clojure library wrapping SmartyStreets' LiveAddress API."
  :url "https://github.com/democracyworks/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :lein-min-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "2.1.0"]
                 [cheshire "5.5.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :plugins [[lein-midje "3.1.0"]]
  :aliases {"test" ["with-profile" "+test" "midje"]})
