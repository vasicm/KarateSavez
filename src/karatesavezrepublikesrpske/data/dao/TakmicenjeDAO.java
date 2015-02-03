/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao;

import java.util.List;
import karatesavezrepublikesrpske.data.dto.TakmicenjeDTO;

/**
 *
 * @author Marko
 */
public interface TakmicenjeDAO {

    public List<TakmicenjeDTO> takmicenja();

    public boolean dodajTakmicenje(TakmicenjeDTO takmicenje);

    public boolean azurirajTakmicenje(TakmicenjeDTO takmicenje);

    public boolean obrisiTakmicenje(int id);
    
    public boolean prijaviTakmicaraNaTakmicenje(int idTakmicara, int idTakmicenja);
}
