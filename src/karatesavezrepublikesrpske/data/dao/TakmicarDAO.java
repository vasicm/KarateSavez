/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao;

import java.util.List;
import karatesavezrepublikesrpske.data.dto.OglasDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;

/**
 *
 * @author Marko
 */
public interface TakmicarDAO {

    public List<TakmicarInfoDTO> tamicari();

    public List<TakmicarInfoDTO> tamicari(String nazivKluba, String nazivTakmicenja, String nazivKategorije);

    public List<TakmicarInfoDTO> tamicari(int idKluba, int idTakmicenja, int idKategorije);

    public List<TakmicarInfoDTO> tamicari(int idTakmicenja, int idKategorije);

    public TakmicarInfoDTO takmicar(int id);

    public boolean dodajTakmicara(TakmicarInfoDTO takmicar);

    public boolean obrisiTakmicara(int id);

    public boolean azurirajTakmicara(TakmicarInfoDTO takmicar);
}
