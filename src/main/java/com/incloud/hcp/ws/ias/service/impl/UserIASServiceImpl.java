package com.incloud.hcp.ws.ias.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.incloud.hcp.dto.GroupDto;
import com.incloud.hcp.ws.OkHttpClientSync;
import com.incloud.hcp.ws.WSConstant;
import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.bean.IASUserInfoResponse;
import com.incloud.hcp.ws.ias.dto.IASUserDto;
import com.incloud.hcp.ws.ias.service.IUserIASService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@Service
public class UserIASServiceImpl implements IUserIASService {

    @Value("${ias.api.url}")
    private String url;

    @Value("${ias.api.user}")
    private String user;

    @Value("${ias.api.pass}")
    private String pass;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final MediaType SCIM_JSON_MEDIA_TYPE = MediaType.parse("application/scim+json");

    @Override
    public IASResponse getUserByEmail(String email) {

        String respuestaJson = "";
        OkHttpClientSync mOkHttpClientSync = new OkHttpClientSync("IAS");

        try {
            IASResponse responseWS = new IASResponse();

            email = email.replace("@test.org", "");
            String filter = "?filter=emails%20eq%20%22" + email + "%22";
            Request request = mOkHttpClientSync.getRequestAuthBasic(String.format("%s/%s/%s", url, WSConstant.PATH_API_IAS_USER, filter), user, pass);
            logger.info("Email para obtener IAS USER -> {}", email);

            Call call = mOkHttpClientSync.getmOkHttpClient().newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                // Respuesta OK: 200
                respuestaJson = response.body().string();
                logger.info("Respuesta IAS -> {}", respuestaJson);
                Map<String, Object> resultMap = new ObjectMapper().readValue(respuestaJson, HashMap.class);

                if (String.valueOf(resultMap.get("totalResults")).equals("1")) {
                    logger.error("ingresa_if_email");
                    responseWS.setStatus(String.valueOf(HttpStatus.OK.value()));
                    responseWS.setMessage("Email " + email + " found");
                    responseWS.setBody(respuestaJson);
                    responseWS.setResultMap(resultMap);
                    responseWS.setResult(this.convertInfoResponse(resultMap));
                } else {
                    logger.error("ingresa_else_email");
                    responseWS.setStatus(String.valueOf(HttpStatus.NO_CONTENT.value()));
                    responseWS.setMessage("Email " + email + " not found");
                    responseWS.setBody("");
                    responseWS.setResultMap(new HashMap<>());
                    IASUserInfoResponse emptyResponse = new IASUserInfoResponse();
                    emptyResponse.setTotalResults(0);             // <--- inicializamos totalResults
                    emptyResponse.setResources(Collections.emptyList());  // <--- inicializamos resources
                    responseWS.setResult(emptyResponse);
                }
                //responseWS = g.fromJson(respuestaJson, ObtenerGuiaResponse.class);
            } else {
                // Respuesta ERROR: 500, 404, 403, entre otros.
                //respuestaJson = String.format("%s : %s", String.valueOf(response.code()), response.message());
                //responseWS = g.fromJson(respuestaJson, ObtenerCompraResponse.class);
                responseWS.setStatus(String.valueOf(response.code()));
                responseWS.setMessage(response.message());
                responseWS.setBody("");
                responseWS.setResultMap(new HashMap<>());
                responseWS.setResult(new IASUserInfoResponse());
            }
            return responseWS;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.getCause());
            // Instanciamos el response del ws e ingresamos los datos de exception
            IASResponse responseWS = new IASResponse();
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            responseWS.setMessageException("Error al consultar IAS: " + ex.getMessage());
            responseWS.setMessageCause(ex.toString());
            responseWS.setCause(ex.getCause());

            responseWS.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseWS.setMessage(responseWS.getMessageException());
            responseWS.setBody("");
            responseWS.setResultMap(new HashMap<>());
            responseWS.setResult(new IASUserInfoResponse());
            return responseWS;
        }
    }

    @Override
    public IASResponse getUserByLoginName(String loginName) {

        String respuestaJson = "";
        OkHttpClientSync mOkHttpClientSync = new OkHttpClientSync("IAS");

        try {
            IASResponse responseWS = new IASResponse();

            loginName = loginName.replace("@test.org", "");
            String filter = "?filter=userName%20eq%20%22" + loginName + "%22";
            Request request = mOkHttpClientSync.getRequestAuthBasic(String.format("%s/%s/%s", url, WSConstant.PATH_API_IAS_USER, filter), user, pass);
            logger.error("LoginName para obtener IAS USER -> {}", loginName);

            Call call = mOkHttpClientSync.getmOkHttpClient().newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                // Respuesta OK: 200
                respuestaJson = response.body().string();
                logger.error("Respuesta IAS LoginName -> {}", respuestaJson);
                ObjectMapper objectMapper = new ObjectMapper();
                IASUserInfoResponse resultMap = objectMapper.readValue(respuestaJson, IASUserInfoResponse.class);
                Map<String, Object> resultMapAsMap = objectMapper.convertValue(resultMap, new TypeReference<Map<String, Object>>() {});
                Gson gson = new Gson();
                //logger.error("Antes de convertir resultMap a Map: " + gson.toJson(resultMap));
                //logger.error("Después de convertir a Map<String, Object>: " + gson.toJson(resultMapAsMap));

                if (resultMap.getTotalResults() == 1) {
                    responseWS.setStatus(String.valueOf(HttpStatus.OK.value()));
                    responseWS.setMessage("LoginName " + loginName + " found");
                    responseWS.setBody(respuestaJson);
                    responseWS.setResultMap(resultMapAsMap);
                    responseWS.setResult(resultMap);
                } else {
                    responseWS.setStatus(String.valueOf(HttpStatus.NO_CONTENT.value()));
                    responseWS.setMessage("LoginName " + loginName + " not found");
                    responseWS.setBody("");
                    responseWS.setResultMap(new HashMap<>());
                    responseWS.setResult(new IASUserInfoResponse());
                }
                //responseWS = g.fromJson(respuestaJson, ObtenerGuiaResponse.class);
            } else {
                // Respuesta ERROR: 500, 404, 403, entre otros.
                //respuestaJson = String.format("%s : %s", String.valueOf(response.code()), response.message());
                //responseWS = g.fromJson(respuestaJson, ObtenerCompraResponse.class);
                responseWS.setStatus(String.valueOf(response.code()));
                responseWS.setMessage(response.message());
                responseWS.setBody("");
                responseWS.setResultMap(new HashMap<>());
                responseWS.setResult(new IASUserInfoResponse());
            }
            return responseWS;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.getCause());
            // Instanciamos el response del ws e ingresamos los datos de exception
            IASResponse responseWS = new IASResponse();
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            responseWS.setMessageException("Error al consultar IAS: " + ex.getMessage());
            responseWS.setMessageCause(ex.toString());
            responseWS.setCause(ex.getCause());

            responseWS.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseWS.setMessage(responseWS.getMessageException());
            responseWS.setBody("");
            responseWS.setResultMap(new HashMap<>());
            responseWS.setResult(new IASUserInfoResponse());
            return responseWS;
        }
    }

    private IASUserInfoResponse convertInfoResponse(Map<String, Object> resultMap) {
        IASUserInfoResponse result = new IASUserInfoResponse();
        result.setTotalResults((Integer) resultMap.get("totalResults"));
        result.setItemsPerPage((Integer) resultMap.get("itemsPerPage"));
        result.setSchemas((List<String>) resultMap.get("schemas"));
        ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) resultMap.get("Resources");
        LinkedHashMap<String, Object> linked = list.get(0);
        LinkedHashMap<String, Object> linkedName = (LinkedHashMap<String, Object>) linked.get("name");

        List<IASUserInfoResponse.Resource> listFinal = new ArrayList<>();

        IASUserInfoResponse.Resource infoUser = new IASUserInfoResponse.Resource();
        infoUser.setDisplayName(String.valueOf(linked.get("displayName")));
        infoUser.setActive((Boolean) linked.get("active"));
        infoUser.setUserName(String.valueOf(linked.get("userName")));
        infoUser.setUserUuid(String.valueOf(linked.get("userUuid")));
        infoUser.setId(String.valueOf(linked.get("id")));

        IASUserInfoResponse.NameInfo nameInfo = new IASUserInfoResponse.NameInfo();
        nameInfo.setGivenName(String.valueOf(linkedName.get("givenName")));
        nameInfo.setFamilyName(String.valueOf(linkedName.get("familyName")));
        infoUser.setName(nameInfo);
        //================================================================group begin========
        List<GroupDto> listaGroup = new ArrayList<GroupDto>();
        Set entrySet = linked.entrySet();
        Iterator it = entrySet.iterator();

        while (it.hasNext()){

            Map.Entry mapEntry=(Map.Entry)it.next();
            if(mapEntry.getKey().toString().equalsIgnoreCase("groups")) {

                ArrayList<LinkedHashMap<String, Object>> listMapGroup = (ArrayList<LinkedHashMap<String, Object>>)mapEntry.getValue();
                for(LinkedHashMap<String, Object> group: listMapGroup) {
                    Set entrySetRow = group.entrySet();
                    Iterator itRow = entrySetRow.iterator();
                    while (itRow.hasNext()){
                        Map.Entry mapEntryRow = (Map.Entry)itRow.next();
                        GroupDto groupRow = new GroupDto();
        				/*if(mapEntryRow.getKey().toString().equalsIgnoreCase("display")) {
        					groupRow.setDisplay(mapEntryRow.getValue().toString());
        				}*/
                        if(mapEntryRow.getKey().toString().equalsIgnoreCase("value")) {
                            if(mapEntryRow.getValue() != null && !mapEntryRow.getValue().toString().equalsIgnoreCase("null")) {
                                groupRow.setValue(mapEntryRow.getValue().toString());
                                groupRow.setDisplay(mapEntryRow.getValue().toString());
                                listaGroup.add(groupRow);
                            }

                        }

                    }

                }

            }

        }
        //=========================================================group end=========
        listFinal.add(infoUser);
        result.setResources(listFinal);
        result.setGroups(listaGroup);
        return result;
    }

    private IASUserInfoResponse convertInfoResponseAll(Map<String, Object> resultMap) {
        IASUserInfoResponse result = new IASUserInfoResponse();
        result.setTotalResults((Integer) resultMap.get("totalResults"));
        result.setItemsPerPage((Integer) resultMap.get("itemsPerPage"));
        result.setSchemas((List<String>) resultMap.get("schemas"));
        result.setResources((List<IASUserInfoResponse.Resource>) resultMap.get("Resources"));
        return result;
    }

    @Override
    public IASResponse getUserAll() {
        String respuestaJson = "";
        OkHttpClientSync mOkHttpClientSync = new OkHttpClientSync("IAS");

        try {
            IASResponse responseWS = new IASResponse();

            Request request = mOkHttpClientSync.getRequestAuthBasic(String.format("%s/%s", url, WSConstant.PATH_API_IAS_USER), user, pass);

            Call call = mOkHttpClientSync.getmOkHttpClient().newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                // Respuesta OK: 200
                respuestaJson = response.body().string();
                logger.info("Respuesta IAS -> {}", respuestaJson);
                Map<String, Object> resultMap = new ObjectMapper().readValue(respuestaJson, HashMap.class);

                if (Integer.parseInt(String.valueOf(resultMap.get("totalResults"))) > 0) {
                    responseWS.setStatus(String.valueOf(HttpStatus.OK.value()));
                    responseWS.setMessage("Users found");
                    responseWS.setBody(respuestaJson);
                    responseWS.setResultMap(resultMap);
                    responseWS.setResult(this.convertInfoResponseAll(resultMap));
                } else {
                    responseWS.setStatus(String.valueOf(HttpStatus.NO_CONTENT.value()));
                    responseWS.setMessage("Users not found");
                    responseWS.setBody("");
                    responseWS.setResultMap(new HashMap<>());
                    responseWS.setResult(new IASUserInfoResponse());
                }
                //responseWS = g.fromJson(respuestaJson, ObtenerGuiaResponse.class);
            } else {
                // Respuesta ERROR: 500, 404, 403, entre otros.
                //respuestaJson = String.format("%s : %s", String.valueOf(response.code()), response.message());
                //responseWS = g.fromJson(respuestaJson, ObtenerCompraResponse.class);
                responseWS.setStatus(String.valueOf(response.code()));
                responseWS.setMessage(response.message());
                responseWS.setBody("");
                responseWS.setResultMap(new HashMap<>());
                responseWS.setResult(new IASUserInfoResponse());
            }
            return responseWS;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.getCause());
            // Instanciamos el response del ws e ingresamos los datos de exception
            IASResponse responseWS = new IASResponse();
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            responseWS.setMessageException("Error al consultar Usuarios IAS: " + ex.getMessage());
            responseWS.setMessageCause(ex.toString());
            responseWS.setCause(ex.getCause());

            responseWS.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseWS.setMessage(responseWS.getMessageException());
            responseWS.setBody("");
            responseWS.setResultMap(new HashMap<>());
            responseWS.setResult(new IASUserInfoResponse());
            return responseWS;
        }
    }

    @Override
    public IASResponse createUserProveedor(IASUserDto iasUserDto) {
        OkHttpClientSync mOkHttpClientSync = new OkHttpClientSync("IAS");
        IASResponse responseWS = new IASResponse();

        try {
            // Ajuste del payload para SCIM
            iasUserDto.setActive(true); // obligatorio
            if (iasUserDto.getEmails() != null) {
                for (IASUserDto.IASValue email : iasUserDto.getEmails()) {
                    email.setPrimary(true); // marcar email principal
                }
            }

            Gson gson = new Gson();
            RequestBody body = RequestBody.create(SCIM_JSON_MEDIA_TYPE, gson.toJson(iasUserDto));
            logger.error("createUserProveedor -> {}, {}", iasUserDto.getDisplayName(), gson.toJson(iasUserDto));

            Request request = mOkHttpClientSync.getRequestPostIASAuthBasic(
                    String.format("%s/%s", url, WSConstant.PATH_API_IAS_USER),
                    body,
                    user,
                    pass
            );

            Call call = mOkHttpClientSync.getmOkHttpClient().newCall(request);
            Response response = call.execute();

            IASUserInfoResponse result = new IASUserInfoResponse();
            result.setResources(Collections.emptyList());
            result.setGroups(Collections.emptyList());
            result.setSchemas(Collections.emptyList());
            result.setTotalResults(0);

            if (response.isSuccessful()) {
                String respuestaJson = response.body().string();
                logger.error("Respuesta create user IAS -> {}", respuestaJson);

                Map<String, Object> resultMap = new ObjectMapper().readValue(respuestaJson, HashMap.class);
                responseWS.setBody(respuestaJson);
                responseWS.setResultMap(resultMap);
                responseWS.setId(String.valueOf(resultMap.get("id")));

                if (resultMap.get("id") != null && !resultMap.get("id").toString().isEmpty()) {
                    responseWS.setStatus(String.valueOf(HttpStatus.OK.value()));
                    responseWS.setMessage("Usuario creado correctamente");

                    IASUserInfoResponse.Resource resource = new IASUserInfoResponse.Resource();
                    resource.setId(String.valueOf(resultMap.get("id")));
                    resource.setUserUuid(String.valueOf(resultMap.get("id")));
                    resource.setUserName((String) resultMap.getOrDefault("userName", iasUserDto.getUserName()));
                    resource.setDisplayName((String) resultMap.getOrDefault("displayName", iasUserDto.getDisplayName()));

                    result.setResources(Collections.singletonList(resource));
                    result.setTotalResults(1);
                } else {
                    responseWS.setStatus(String.valueOf(HttpStatus.NO_CONTENT.value()));
                    responseWS.setMessage("Usuario no creado");
                }
            } else {
                // manejar errores 400, 409, 500 de IAS
                String errorBody = response.body() != null ? response.body().string() : "";
                logger.error("Error al crear usuario IAS: HTTP {} -> {}", response.code(), errorBody);

                responseWS.setStatus(String.valueOf(response.code()));
                responseWS.setMessage(response.message());
                responseWS.setBody(errorBody);
            }

            responseWS.setResult(result); // siempre setear result para evitar NPE
            return responseWS;

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            IASUserInfoResponse emptyResult = new IASUserInfoResponse();
            emptyResult.setResources(Collections.emptyList());
            emptyResult.setGroups(Collections.emptyList());
            emptyResult.setSchemas(Collections.emptyList());
            emptyResult.setTotalResults(0);

            responseWS.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseWS.setMessage("Error al crear Usuario IAS: " + ex.getMessage());
            responseWS.setMessageException(ex.getMessage());
            responseWS.setMessageCause(ex.toString());
            responseWS.setCause(ex.getCause());
            responseWS.setBody("");
            responseWS.setResultMap(new HashMap<>());
            responseWS.setId("");
            responseWS.setResult(emptyResult);

            return responseWS;
        }

    }



    @Override
    public IASResponse deleteUsuarioIas(String usuarioId) {
        String respuestaJson = "";
        OkHttpClientSync mOkHttpClientSync = new OkHttpClientSync("IAS");

        try {
            IASResponse responseWS = new IASResponse();

            // Construimos la URL para eliminar el usuario
            String urlDelete = String.format("%s/%s/%s", url, "service/scim/Users", usuarioId);
            logger.info("deleteUsuarioIas -> {}", urlDelete);

            // Preparamos la request DELETE con autenticación básica
            Request request = mOkHttpClientSync.getRequestDeleteIASAuthBasic(urlDelete, user, pass);

            Call call = mOkHttpClientSync.getmOkHttpClient().newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                // Usuario eliminado correctamente
                respuestaJson = response.body() != null ? response.body().string() : "";
                logger.info("Respuesta deleteUsuarioIas -> {}", respuestaJson);

                responseWS.setStatus(String.valueOf(HttpStatus.OK.value()));
                responseWS.setMessage("Usuario eliminado correctamente");
                responseWS.setBody(respuestaJson);
                responseWS.setResultMap(new HashMap<>());
                responseWS.setId(usuarioId);

            } else {
                // Error al eliminar usuario
                responseWS.setStatus(String.valueOf(response.code()));
                responseWS.setMessage(response.message());
                responseWS.setBody("");
                responseWS.setResultMap(new HashMap<>());
                responseWS.setId("");
            }

            return responseWS;

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.getCause());

            IASResponse responseWS = new IASResponse();
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            responseWS.setMessageException("Error al eliminar Usuario IAS: " + ex.getMessage());
            responseWS.setMessageCause(ex.toString());
            responseWS.setCause(ex.getCause());

            responseWS.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            responseWS.setMessage(responseWS.getMessageException());
            responseWS.setBody("");
            responseWS.setResultMap(new HashMap<>());
            responseWS.setId("");

            return responseWS;
        }
    }


}
