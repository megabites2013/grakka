package grakka


import grails.rest.*
import grails.converters.*

class SearchController {

    def index() {
        render(contentType: 'application/json') {
            searchId = UUID.randomUUID().toString()
            results = array { [] }
        }
    }
}
