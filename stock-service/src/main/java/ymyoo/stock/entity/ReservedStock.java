package ymyoo.stock.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ymyoo.stock.Status;
import ymyoo.stock.dto.StockAdjustment;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;

@Entity
public class ReservedStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String resources;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public ReservedStock() {
    }

    public ReservedStock(StockAdjustment stockAdjustment) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.resources = objectMapper.writeValueAsString(stockAdjustment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.created = new Date();
    }

    public Long getId() {
        return id;
    }

    public StockAdjustment getResources() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(this.resources, StockAdjustment.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }
}

