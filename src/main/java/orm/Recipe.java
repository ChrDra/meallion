/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.TopDocs;
import utils.Log;

/**
 *
 * @author Christoph
 */
@Entity(name = "Recipe")
@Table(name = "recipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r"),
    @NamedQuery(name = "Recipe.findById", query = "SELECT r FROM Recipe r WHERE r.id = :id"),
    @NamedQuery(name = "Recipe.findByName", query = "SELECT r FROM Recipe r WHERE r.name = :name"),
    @NamedQuery(name = "Recipe.findBySeason", query = "SELECT r FROM Recipe r WHERE r.season = :season"),
    @NamedQuery(name = "Recipe.findByPrepTime", query = "SELECT r FROM Recipe r WHERE r.prepTime = :prepTime"),
    @NamedQuery(name = "Recipe.findByVegetarian", query = "SELECT r FROM Recipe r WHERE r.vegetarian = :vegetarian"),
    @NamedQuery(name = "Recipe.findByVegan", query = "SELECT r FROM Recipe r WHERE r.vegan = :vegan"),
    @NamedQuery(name = "Recipe.findByKeyword", query = "SELECT r FROM Recipe r WHERE r.keyword = :keyword"),
    @NamedQuery(name = "Recipe.findByTs", query = "SELECT r FROM Recipe r WHERE r.ts = :ts")}) 
public class Recipe implements Serializable {

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
    @Column(name = "long_descr")
    private String long_descr;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "short_descr")
    private String short_descr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prio")
    private String prio;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "source")
    private String source;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "body")
    private String body;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "tags")
    private String tags;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "season")
    private String season;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "keyword")
    private String keyword;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prep_time")
    private int prepTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "proposed_portions")
    private int proposedPortions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vegetarian")
    private boolean vegetarian;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vegan")
    private boolean vegan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hide")
    private boolean hide;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "img_url_small")
    private String imgUrl_small;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "img_url_large")
    private String imgUrl_large;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ts")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts;
    @OneToMany(
        mappedBy = "recipe", 
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch=FetchType.EAGER
    )
    private List<IngredientRecipe> ingredientrecipe = new ArrayList<>();

    @Transient
    private double price;
    
    public Recipe() {
        price = Double.NaN;
    }

    public Recipe(Integer id) {
        this.id = id;
        price = Double.NaN;
    }
    
    public String GetPriceString(){
        DecimalFormat df = new DecimalFormat("0.00");
        return "&#x20AC "+df.format(this.getPrice()); 
    }
    
    public double getPrice(){
        if(Double.isNaN(this.price)){
            double price = 0;
            for(IngredientRecipe current_ir : this.getIngredientRecipe()){
                    price += current_ir.getIngredient().getPrice()*current_ir.getAmount();
            }
            this.price = price;
            return price;
        }
        return this.price;
    }

    public Recipe(Integer id, String name, String long_descr, String short_descr, String prio, String source, String body, String tags, String season, String keyword, int prepTime, int proposedPortions, boolean vegetarian, boolean vegan, boolean hide, String imgUrl_small, String imgUrl_large, Date ts, double price) {
        this.id = id;
        this.name = name;
        this.long_descr = long_descr;
        this.short_descr = short_descr;
        this.prio = prio;
        this.source = source;
        this.body = body;
        this.tags = tags;
        this.season = season;
        this.keyword = keyword;
        this.prepTime = prepTime;
        this.proposedPortions = proposedPortions;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.hide = hide;
        this.imgUrl_small = imgUrl_small;
        this.imgUrl_large = imgUrl_large;
        this.ts = ts;
        this.price = price;
    }
            

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPrice(double price) {
        this.price = price;
    }
        
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
        
    public List<IngredientRecipe> getIngredientRecipe() {
        return ingredientrecipe;
    }

    public void setIngredientRecipe(List<IngredientRecipe> ingredientrecipe) {
        this.ingredientrecipe = ingredientrecipe;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLong_descr() {
        return long_descr;
    }

    public void setLong_descr(String long_descr) {
        this.long_descr = long_descr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getShort_descr() {
        return short_descr;
    }

    public void setShort_descr(String short_descr) {
        this.short_descr = short_descr;
    }
    
    public String getBody() {
        return body;
    }

    public int getProposedPortions() {
        return proposedPortions;
    }

    public void setProposedPortions(int proposedPortions) {
        this.proposedPortions = proposedPortions;
    }
    

    public void setBody(String body) {
        this.body = body;
    }

    public List<IngredientRecipe> getIngredientrecipe() {
        return ingredientrecipe;
    }

    public void setIngredientrecipe(List<IngredientRecipe> ingredientrecipe) {
        this.ingredientrecipe = ingredientrecipe;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean getVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public String getImgUrl_small() {
        return imgUrl_small;
    }

    public void setImgUrl_small(String imgUrl_small) {
        this.imgUrl_small = imgUrl_small;
    }

    public String getImgUrl_large() {
        return imgUrl_large;
    }

    public void setImgUrl_large(String imgUrl_large) {
        this.imgUrl_large = imgUrl_large;
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
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
                
        if ((this.id == null && other.getId() != null) || (this.id != null && !this.id.equals(other.getId()))) {
            return false;
        }

        return true;
    }
    
    public void CreateFromTopHits(Document d){
        this.id = Integer.parseInt(d.get("id"));
        this.keyword = d.get("keyword");
        this.name = d.get("name");
        this.short_descr = d.get("short_descr");Log.w("id5");
        this.long_descr = d.get("long_descr");Log.w("id6");
        this.imgUrl_large = d.get("img_url_large");
        this.imgUrl_small = d.get("img_url_small");
        Log.w("id7");
        Log.w("STRING : "+d.get("store_prep_time"));
        Log.w("NUMERIC SHALL BE: "+d.getField("store_prep_time").numericValue().intValue());
        this.prepTime = d.getField("store_prep_time").numericValue().intValue();Log.w("id8");
        this.price = (d.getField("store_budget").numericValue().doubleValue())/100.0;Log.w("id9");        
    } 

    @Override
    public String toString() {
        return "orm.Recipe[ id=" + id + " ]";
    }
    
    
    
}
