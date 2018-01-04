package com.lltvcn.freefont.core.layer;

public interface SourceLoader<T> {

        T loadByName(String name);

}