package com.incloud.hcp.service;

import com.incloud.hcp.domain.StorageDocument;

public interface StorageDocumentService {

    StorageDocument getDocumentByPath(String path, boolean includeBase64);
}
