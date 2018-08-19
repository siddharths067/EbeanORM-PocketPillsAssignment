package models.db;

import akka.parboiled2.RuleTrace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseModel extends Model {

    @Id
    @JsonIgnore
    @Column(name = "id", unique = true)
    Long id;

    @WhenCreated
    @JsonIgnore
    Timestamp whenCreated;

    @SoftDelete
    @JsonIgnore
    @JsonProperty("enabled")
    @Index
    boolean enabled;

    @WhenModified
    @JsonIgnore
    Timestamp whenModified;

    @WhoCreated
    @JsonIgnore
    Long whoCreated;

    @WhoModified
    @JsonIgnore
    Long whoModified;

    @Version
    @JsonIgnore
    Long version;

    public long getId(){
        return this.id;
    }

    public void setEnabled(boolean isEnabled){
        this.enabled = isEnabled;
    }
    public boolean getEnabled(){return this.enabled;}
}
