package com.alper.exception;

import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler exceptionHandler;

    public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return exceptionHandler;
    }

    @Override
    public void handle() throws FacesException {

        // tüm unhandled exceptionları iteratora atıyor
        Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

        // iterator boşalana kadar yazdırıyor
        while (iterator.hasNext()) {
            // exception ın event ve contextini alıyor
            ExceptionQueuedEvent event = iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Throwable throwable = context.getException();
            File file = new File("test.log");
            PrintStream ps = null;
            try {
                // hatayı flasha aktarma
                Flash flash = facesContext.getExternalContext().getFlash();
                flash.put("errorDetails", throwable.getMessage());

                ps = new PrintStream(file);

                NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
                // hata ile karşılaşırsak error.xhtml e yollayacak
                navigationHandler.handleNavigation(facesContext, null, "/error.xhtml?faces-redirect=true");
                facesContext.renderResponse();
            } catch (Exception ex) {
                ex.printStackTrace(ps);
            } finally {
                iterator.remove();
                ps.close();
            }
        }
        getWrapped().handle();
    }
}