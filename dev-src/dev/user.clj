(ns dev.user
  (:require
   [democracyworks.smartystreets.core :as smartystreets]
   [democracyworks.smartystreets.us-street :as us-street]
   [democracyworks.smartystreets.us-zipcode :as us-zipcode]))

(comment "Construct a client with your own API key."

(def client
  (smartystreets/client
   "your-auth-id"
   "your-auth-token"))

)
