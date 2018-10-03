/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;    
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author Christoph
 */
@Entity(name = "Ingredient")
@Table(name = "ingredient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingredient.findAll", query = "SELECT i FROM Ingredient i"),
    @NamedQuery(name = "Ingredient.findById", query = "SELECT i FROM Ingredient i WHERE i.id = :id"),
    @NamedQuery(name = "Ingredient.findByName", query = "SELECT i FROM Ingredient i WHERE i.name = :name"),
    @NamedQuery(name = "Ingredient.findByUnit", query = "SELECT i FROM Ingredient i WHERE i.unit = :unit"),
    @NamedQuery(name = "Ingredient.findByPrice", query = "SELECT i FROM Ingredient i WHERE i.price = :price"),
    @NamedQuery(name = "Ingredient.findBySeason", query = "SELECT i FROM Ingredient i WHERE i.season = :season"),
    @NamedQuery(name = "Ingredient.findByDurability", query = "SELECT i FROM Ingredient i WHERE i.durability = :durability"),
    @NamedQuery(name = "Ingredient.findByTs", query = "SELECT i FROM Ingredient i WHERE i.ts = :ts")})
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descr")
    private String descr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "unit")
    private String unit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "friendly_unit")
    private String friendly_unit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "season")
    private String season;
    @Basic(optional = false)
    @NotNull
    @Column(name = "durability")
    private int durability;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "img_url")
    private String imgUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "min_amount")
    private double min_amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "friendly_unit_conv")
    private double friendly_unit_conv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts;
    @ManyToOne( cascade = { CascadeType.ALL } )
    @JoinColumn(name = "parent_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Ingredient parent;
    @OneToMany(mappedBy = "parent")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Ingredient> children;


    public Ingredient() {
    }

    public Ingredient(Integer id) {
        this.id = id;
    }

    public Ingredient(Integer id, String name, String descr, String unit, double price, String season, int durability, String imgUrl, Date ts) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.unit = unit;
        this.price = price;
        this.season = season;
        this.durability = durability;
        this.imgUrl = imgUrl;
        this.ts = ts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }
    
    public boolean hasFriendlyUnit(){
        if(this.unit.equals(this.friendly_unit))
            return false;
        return true;
    }
    
    public String getEuroPriceString(double multiplier) {
        //return "€"+(Math.round((this.getPrice()*multiplier)*100.0)/100.0);
        DecimalFormat df = new DecimalFormat("0.00");
        return "€"+df.format(this.getPrice()*multiplier); 
    }
    
    public String getPriceString(double multiplier) {
        //return "€"+(Math.round((this.getPrice()*multiplier)*100.0)/100.0);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(this.getPrice()*multiplier); 
    }

    public String getFriendly_unit() {
        return friendly_unit;
    }

    public void setFriendly_unit(String friendly_unit) {
        this.friendly_unit = friendly_unit;
    }

    public double getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(double min_amount) {
        this.min_amount = min_amount;
    }

    public double getFriendly_unit_conv() {
        return friendly_unit_conv;
    }

    public void setFriendly_unit_conv(double friendly_unit_conv) {
        this.friendly_unit_conv = friendly_unit_conv;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{\"id\":\""+this.id+"\",");
        str.append("\"name\":\""+this.name+"\",");
        str.append("\"season\":\""+this.season+"\",");
        str.append("\"durability\":\""+this.durability+"\",");
        str.append("\"unit\":\""+this.unit+"\",");
        str.append("\"friendly_unit\":\""+this.friendly_unit+"\",");
        str.append("\"imgUrl\":\""+this.imgUrl+"\",");
        str.append("\"min_amount\":\""+this.min_amount+"\",");
        str.append("\"price\":\""+this.price+"\",");
        str.append("\"ts\":\""+this.ts+"\",");
        str.append("\"parent name\":\""+this.parent.name+"\"");
        str.append("\"children names\":[");
        for(int i=0; i<this.children.size();i++){
            str.append("\""+this.children.get(i).name+"\"");
            if(!(i+1>=this.children.size())){
                str.append(",");
            }
            
        }
        str.append("]}");
        return str.toString();
    }
    
}
