package com.andymcg.northumberlandbadmintonadmin

import com.fasterxml.jackson.annotation.ObjectIdGenerator
import com.fasterxml.jackson.annotation.ObjectIdResolver
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver

class DedupingObjectIdResolver : SimpleObjectIdResolver() {
    override fun bindItem(id: ObjectIdGenerator.IdKey?, ob: Any?) {
        if (_items == null) {
            _items = mutableMapOf()
        }
        _items[id] = ob
    }

    override fun newForDeserialization(context: Any?): ObjectIdResolver {
        return super.newForDeserialization(context)
    }
}