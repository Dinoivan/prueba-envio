package com.incloud.hcp.populate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface Populater<SOURCE, TARGET> {
    TARGET toDto(SOURCE source);

    SOURCE toEntity(TARGET target);

    default List<TARGET> toDto(List<SOURCE> listSource) {
        return Optional.ofNullable(listSource)
                .map(list -> {
                    List<TARGET> listTarget = new ArrayList<>();
                    list.stream().map(this::toDto).forEach(listTarget::add);
                    return listTarget;
                })
                .orElse(Collections.emptyList());
    }

    default List<SOURCE> toEntity(List<TARGET> listTarget) {
        return Optional.ofNullable(listTarget)
                .map(list -> {
                    List<SOURCE> listSource = new ArrayList<>();
                    list.stream().map(this::toEntity).forEach(listSource::add);
                    return listSource;
                })
                .orElse(Collections.emptyList());
    }
}
