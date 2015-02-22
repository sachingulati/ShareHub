package sharehub


import grails.test.mixin.*
import spock.lang.*

@TestFor(ResourceStatusController)
@Mock(ResourceStatus)
class ResourceStatusControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.resourceStatusInstanceList
        model.resourceStatusInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.resourceStatusInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def resourceStatus = new ResourceStatus()
        resourceStatus.validate()
        controller.save(resourceStatus)

        then: "The create view is rendered again with the correct model"
        model.resourceStatusInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        resourceStatus = new ResourceStatus(params)

        controller.save(resourceStatus)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/resourceStatus/show/1'
        controller.flash.message != null
        ResourceStatus.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def resourceStatus = new ResourceStatus(params)
        controller.show(resourceStatus)

        then: "A model is populated containing the domain instance"
        model.resourceStatusInstance == resourceStatus
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def resourceStatus = new ResourceStatus(params)
        controller.edit(resourceStatus)

        then: "A model is populated containing the domain instance"
        model.resourceStatusInstance == resourceStatus
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/resourceStatus/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def resourceStatus = new ResourceStatus()
        resourceStatus.validate()
        controller.update(resourceStatus)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.resourceStatusInstance == resourceStatus

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        resourceStatus = new ResourceStatus(params).save(flush: true)
        controller.update(resourceStatus)

        then: "A redirect is issues to the show action"
        response.redirectedUrl == "/resourceStatus/show/$resourceStatus.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/resourceStatus/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def resourceStatus = new ResourceStatus(params).save(flush: true)

        then: "It exists"
        ResourceStatus.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(resourceStatus)

        then: "The instance is deleted"
        ResourceStatus.count() == 0
        response.redirectedUrl == '/resourceStatus/index'
        flash.message != null
    }
}
