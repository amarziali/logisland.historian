package com.hurence.logisland.historian.rest.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.hurence.logisland.historian.rest.v1.model.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;


import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
* Alert
*/
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-19T12:25:30.271+02:00")

@SolrDocument(solrCoreName = "historian")
public class Alert  implements Serializable {
        @JsonProperty("record_type")
        @Indexed(name = "record_type")
        private String recordType = "alert";

        @JsonProperty("message")
        @Indexed(name = "message")
        private String message = null;

        @JsonProperty("severity")
        @Indexed(name = "severity")
        private Integer severity = null;

        @JsonProperty("tag_id")
        @Indexed(name = "tag_id")
        private Tag tagId = null;

        public Alert recordType(String recordType) {
        this.recordType = recordType;
        return this;
        }

    /**
        * Get recordType
    * @return recordType
    **/
        @JsonProperty("record_type")
    @ApiModelProperty(value = "")
    

  public String getRecordType() {
    return recordType;
    }

        public void setRecordType(String recordType) {
        this.recordType = recordType;
        }

        public Alert message(String message) {
        this.message = message;
        return this;
        }

    /**
        * Get message
    * @return message
    **/
        @JsonProperty("message")
    @ApiModelProperty(value = "")
    

  public String getMessage() {
    return message;
    }

        public void setMessage(String message) {
        this.message = message;
        }

        public Alert severity(Integer severity) {
        this.severity = severity;
        return this;
        }

    /**
        * Get severity
    * @return severity
    **/
        @JsonProperty("severity")
    @ApiModelProperty(value = "")
    

  public Integer getSeverity() {
    return severity;
    }

        public void setSeverity(Integer severity) {
        this.severity = severity;
        }

        public Alert tagId(Tag tagId) {
        this.tagId = tagId;
        return this;
        }

    /**
        * Get tagId
    * @return tagId
    **/
        @JsonProperty("tag_id")
    @ApiModelProperty(value = "")
    
  @Valid

  public Tag getTagId() {
    return tagId;
    }

        public void setTagId(Tag tagId) {
        this.tagId = tagId;
        }


    @Override
    public boolean equals(java.lang.Object o) {
    if (this == o) {
    return true;
    }
    if (o == null || getClass() != o.getClass()) {
    return false;
    }
        Alert alert = (Alert) o;
        return Objects.equals(this.recordType, alert.recordType) &&
        Objects.equals(this.message, alert.message) &&
        Objects.equals(this.severity, alert.severity) &&
        Objects.equals(this.tagId, alert.tagId);
    }

    @Override
    public int hashCode() {
    return Objects.hash(recordType, message, severity, tagId);
    }


@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("class Alert {\n");

sb.append("    recordType: ").append(toIndentedString(recordType)).append("\n");
sb.append("    message: ").append(toIndentedString(message)).append("\n");
sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
sb.append("    tagId: ").append(toIndentedString(tagId)).append("\n");
sb.append("}");
return sb.toString();
}

/**
* Convert the given object to string with each line indented by 4 spaces
* (except the first line).
*/
private String toIndentedString(java.lang.Object o) {
if (o == null) {
return "null";
}
return o.toString().replace("\n", "\n    ");
}
}
