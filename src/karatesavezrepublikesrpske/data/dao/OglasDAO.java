/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao;

import java.util.List;
import karatesavezrepublikesrpske.data.dto.OglasDTO;

/**
 *
 * @author Marko
 */
public interface OglasDAO {

    public List<OglasDTO> oglasi();

    public OglasDTO oglas(int id);

    public boolean dodajOglas(OglasDTO oglas);

    public boolean obrisiOglas(int id);

    public boolean azurirajOglas(OglasDTO oglas);
}
