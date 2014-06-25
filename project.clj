(defproject clj-smartystreets "0.1.2"
  :description "A Clojure library wrapping SmartyStreets' LiveAddress API."
  :url "https://github.com/turbovote/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :lein-min-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "0.7.6"]
                 [cheshire "5.3.1"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}}
  :plugins [[lein-midje "3.1.0"]]
  :aliases {"test" ["with-profile" "+test" "midje"]})
