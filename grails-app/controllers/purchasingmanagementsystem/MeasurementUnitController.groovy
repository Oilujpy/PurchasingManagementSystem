package purchasingmanagementsystem

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class MeasurementUnitController {

    MeasurementUnitService measurementUnitService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond measurementUnitService.list(params), model:[measurementUnitCount: measurementUnitService.count()]
    }

    def show(Long id) {
        respond measurementUnitService.get(id)
    }

    def create() {
        respond new MeasurementUnit(params)
    }

    def save(MeasurementUnit measurementUnit) {
        if (measurementUnit == null) {
            notFound()
            return
        }

        try {
            measurementUnitService.save(measurementUnit)
        } catch (ValidationException e) {
            respond measurementUnit.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'measurementUnit.label', default: 'MeasurementUnit'), measurementUnit.id])
                redirect measurementUnit
            }
            '*' { respond measurementUnit, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond measurementUnitService.get(id)
    }

    def update(MeasurementUnit measurementUnit) {
        if (measurementUnit == null) {
            notFound()
            return
        }

        try {
            measurementUnitService.save(measurementUnit)
        } catch (ValidationException e) {
            respond measurementUnit.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'measurementUnit.label', default: 'MeasurementUnit'), measurementUnit.id])
                redirect measurementUnit
            }
            '*'{ respond measurementUnit, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        measurementUnitService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'measurementUnit.label', default: 'MeasurementUnit'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'measurementUnit.label', default: 'MeasurementUnit'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}