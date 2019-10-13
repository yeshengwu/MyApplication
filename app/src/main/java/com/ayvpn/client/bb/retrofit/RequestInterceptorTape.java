package com.ayvpn.client.bb.retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidu on 15/11/11.
 */
public class RequestInterceptorTape implements RequestInterceptor.RequestFacade, RequestInterceptor {

    private final List<CommandWithParams> tape = new ArrayList<CommandWithParams>();

    @Override
    public void addHeader(String name, String value) {
        tape.add(new CommandWithParams(Command.ADD_HEADER, name, value));
    }

    @Override
    public void addPathParam(String name, String value) {

    }

    @Override
    public void addEncodedPathParam(String name, String value) {

    }

    @Override
    public void addQueryParam(String name, String value) {

    }

    @Override
    public void addEncodedQueryParam(String name, String value) {

    }

    @Override
    public void intercept(RequestFacade request) {
        for (CommandWithParams cwp : tape) {
            cwp.command.intercept(request, cwp.name, cwp.value);
        }
    }

    /*@Override
    public void intercept22(RequestFacade request) {
        for (CommandWithParams cwp : tape) {
            cwp.command.intercept22(request, cwp.name, cwp.value);
        }
    }*/

    private enum Command {
        ADD_HEADER {
            @Override
            void intercept(RequestFacade facade, String name, String value) {
                facade.addHeader(name, value);
            }

//            @Override
//            void intercept22(RequestFacade facade, String name, String value) {
//
//            }
        };

        abstract void intercept(RequestFacade facade, String name, String value);
//        abstract void intercept22(RequestFacade facade, String name, String value);
    }

    private static final class CommandWithParams {
        final Command command;
        final String name;
        final String value;

        CommandWithParams(Command command, String name, String value) {
            this.command = command;
            this.name = name;
            this.value = value;
        }
    }
}
