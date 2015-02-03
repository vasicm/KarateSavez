/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao;

import java.util.List;
import karatesavezrepublikesrpske.data.dto.BorbePojdeinacnoInfoDTO;

/**
 *
 * @author Marko
 */
public interface KategorijaBorbeDAO {

    public List<BorbePojdeinacnoInfoDTO> sveKategorije();

    public List<BorbePojdeinacnoInfoDTO> sveKategorijeNaTakmicenju(int idTakmicenja);

    public boolean prijaviTakmicara(long idTakmicara, int idKategorije, int idTakmicenja);

    public boolean odjaviTakmicara(long idTakmicara, int idKategorije, int idTakmicenja);
}
