package com.dacklabs.spookyaction.client.gin;

import com.dacklabs.spookyaction.client.main.SpookyActionView;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MainModule.class)
public interface MainInjector extends Ginjector {
	SpookyActionView getMainView();
}
