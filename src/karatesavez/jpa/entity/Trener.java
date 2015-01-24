/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "trener")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trener.findAll", query = "SELECT t FROM Trener t"),
    @NamedQuery(name = "Trener.findByOpis", query = "SELECT t FROM Trener t WHERE t.opis = :opis"),
    @NamedQuery(name = "Trener.findByJmb", query = "SELECT t FROM Trener t WHERE t.jmb = :jmb")})
public class Trener implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "Opis")
    private String opis;
    @Id
    @Basic(optional = false)
    @Column(name = "JMB")
    private Long jmb;
    @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Clan clan;

    public Trener() {
    }

    public Trener(Long jmb) {
        this.jmb = jmb;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Long getJmb() {
        return jmb;
    }

    public void setJmb(Long jmb) {
        this.jmb = jmb;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jmb != null ? jmb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trener)) {
            return false;
        }
        Trener other = (Trener) object;
        if ((this.jmb == null && other.jmb != null) || (this.jmb != null && !this.jmb.equals(other.jmb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Trener[ jmb=" + jmb + " ]";
    }
    
}
