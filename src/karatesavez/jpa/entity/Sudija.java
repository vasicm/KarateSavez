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
@Table(name = "sudija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sudija.findAll", query = "SELECT s FROM Sudija s"),
    @NamedQuery(name = "Sudija.findByJmb", query = "SELECT s FROM Sudija s WHERE s.jmb = :jmb"),
    @NamedQuery(name = "Sudija.findByNivo", query = "SELECT s FROM Sudija s WHERE s.nivo = :nivo")})
public class Sudija implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "JMB")
    private Long jmb;
    @Column(name = "Nivo")
    private String nivo;
    @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Clan clan;

    public Sudija() {
    }

    public Sudija(Long jmb) {
        this.jmb = jmb;
    }

    public Long getJmb() {
        return jmb;
    }

    public void setJmb(Long jmb) {
        this.jmb = jmb;
    }

    public String getNivo() {
        return nivo;
    }

    public void setNivo(String nivo) {
        this.nivo = nivo;
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
        if (!(object instanceof Sudija)) {
            return false;
        }
        Sudija other = (Sudija) object;
        if ((this.jmb == null && other.jmb != null) || (this.jmb != null && !this.jmb.equals(other.jmb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Sudija[ jmb=" + jmb + " ]";
    }
    
}
