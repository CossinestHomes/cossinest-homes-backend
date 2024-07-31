package com.cossinest.homes.payload;

import com.cossinest.homes.domain.enums.RoleType;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

//public class RoleTypeDeserializer extends JsonDeserializer<RoleType> {
//
////    @Override
////    public RoleType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
////        String role= jsonParser.getText();
////        return RoleType.valueOf(role.toUpperCase());
////    }
//}
