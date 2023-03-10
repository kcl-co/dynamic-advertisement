package com.share.co.kcl.dad.appserver.vo;

import com.share.co.kcl.dad.repository.model.domain.ConfigAttributeValueDo;
import com.share.co.kcl.dad.repository.model.po.dos.MultiConfigAttributeValueSelectResult;
import com.share.co.kcl.dad.repository.model.po.entities.DadConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ConfigListQueryResponse {

    private List<ListItem> result;

    public ConfigListQueryResponse() {
    }

    public ConfigListQueryResponse(List<DadConfig> dadConfigList, List<MultiConfigAttributeValueSelectResult> extraAttributeList) {
        Map<Long, List<MultiConfigAttributeValueSelectResult>> extraAttributeMap =
                Optional.ofNullable(extraAttributeList).orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.groupingBy(MultiConfigAttributeValueSelectResult::getConfigId));
        this.result = Optional.ofNullable(dadConfigList).orElse(Collections.emptyList())
                .stream()
                .map(dadConfig -> {
                    ListItem item = new ListItem();
                    item.setCode(dadConfig.getCode());
                    item.setName(dadConfig.getName());
                    item.setImage(dadConfig.getImage());
                    item.setSort(dadConfig.getSort());
                    item.setIsEnabled(dadConfig.getIsEnabled());
                    item.setExtraAttribute(
                            Optional.ofNullable(extraAttributeMap.get(dadConfig.getId())).orElse(Collections.emptyList())
                                    .stream()
                                    .map(ConfigAttributeValueDo::new)
                                    .collect(Collectors.toMap(ConfigAttributeValueDo::getKey, ConfigAttributeValueDo::getDeserializeValue)));
                    return item;
                }).collect(Collectors.toList());
    }

    @Data
    public static class ListItem {

        @ApiModelProperty(value = "??????")
        private String code;

        @ApiModelProperty(value = "??????")
        private String name;

        @ApiModelProperty(value = "??????")
        private String image;

        @ApiModelProperty(value = "?????? ?????????????????????")
        private Integer sort;

        @ApiModelProperty(value = "??????")
        private Boolean isEnabled;

        @ApiModelProperty(value = "????????????")
        private Map<String, Object> extraAttribute;

    }
}
