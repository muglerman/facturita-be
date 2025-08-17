package com.cna.facturita.core.loader.tenant;

import com.cna.facturita.core.model.tenant.Departamento;
import com.cna.facturita.core.model.tenant.Provincia;
import com.cna.facturita.core.repository.tenant.DepartamentoRepository;
import com.cna.facturita.core.repository.tenant.ProvinciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProvinciaDataLoader {

    private static final Logger log = LoggerFactory.getLogger(ProvinciaDataLoader.class);

    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoRepository departamentoRepository;

    public ProvinciaDataLoader(ProvinciaRepository provinciaRepository, DepartamentoRepository departamentoRepository) {
        this.provinciaRepository = provinciaRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public void cargaInicial() {
        log.info("=== Iniciando carga de datos demo para Provincias ===");

        if (provinciaRepository.count() > 0) {
            log.info("Ya existen provincias en la base de datos. Saltando creación de datos demo.");
            return;
        }
        String[][] data = {
                { "0101", "01", "Chachapoyas" },
                { "0102", "01", "Bagua" },
                { "0103", "01", "Bongará" },
                { "0104", "01", "Condorcanqui" },
                { "0105", "01", "Luya" },
                { "0106", "01", "Rodríguez de Mendoza" },
                { "0107", "01", "Utcubamba" },
                { "0201", "02", "Huaraz" },
                { "0202", "02", "Aija" },
                { "0203", "02", "Antonio Raymondi" },
                { "0204", "02", "Asunción" },
                { "0205", "02", "Bolognesi" },
                { "0206", "02", "Carhuaz" },
                { "0207", "02", "Carlos Fermín Fitzcarrald" },
                { "0208", "02", "Casma" },
                { "0209", "02", "Corongo" },
                { "0210", "02", "Huari" },
                { "0211", "02", "Huarmey" },
                { "0212", "02", "Huaylas" },
                { "0213", "02", "Mariscal Luzuriaga" },
                { "0214", "02", "Ocros" },
                { "0215", "02", "Pallasca" },
                { "0216", "02", "Pomabamba" },
                { "0217", "02", "Recuay" },
                { "0218", "02", "Santa" },
                { "0219", "02", "Sihuas" },
                { "0220", "02", "Yungay" },
                { "0301", "03", "Abancay" },
                { "0302", "03", "Andahuaylas" },
                { "0303", "03", "Antabamba" },
                { "0304", "03", "Aymaraes" },
                { "0305", "03", "Cotabambas" },
                { "0306", "03", "Chincheros" },
                { "0307", "03", "Grau" },
                { "0401", "04", "Arequipa" },
                { "0402", "04", "Camaná" },
                { "0403", "04", "Caravelí" },
                { "0404", "04", "Castilla" },
                { "0405", "04", "Caylloma" },
                { "0406", "04", "Condesuyos" },
                { "0407", "04", "Islay" },
                { "0408", "04", "La Uniòn" },
                { "0501", "05", "Huamanga" },
                { "0502", "05", "Cangallo" },
                { "0503", "05", "Huanca Sancos" },
                { "0504", "05", "Huanta" },
                { "0505", "05", "La Mar" },
                { "0506", "05", "Lucanas" },
                { "0507", "05", "Parinacochas" },
                { "0508", "05", "Pàucar del Sara Sara" },
                { "0509", "05", "Sucre" },
                { "0510", "05", "Víctor Fajardo" },
                { "0511", "05", "Vilcas Huamán" },
                { "0601", "06", "Cajamarca" },
                { "0602", "06", "Cajabamba" },
                { "0603", "06", "Celendín" },
                { "0604", "06", "Chota" },
                { "0605", "06", "Contumazá" },
                { "0606", "06", "Cutervo" },
                { "0607", "06", "Hualgayoc" },
                { "0608", "06", "Jaén" },
                { "0609", "06", "San Ignacio" },
                { "0610", "06", "San Marcos" },
                { "0611", "06", "San Miguel" },
                { "0612", "06", "San Pablo" },
                { "0613", "06", "Santa Cruz" },
                { "0701", "07", "Prov. Const. del Callao" },
                { "0801", "08", "Cusco" },
                { "0802", "08", "Acomayo" },
                { "0803", "08", "Anta" },
                { "0804", "08", "Calca" },
                { "0805", "08", "Canas" },
                { "0806", "08", "Canchis" },
                { "0807", "08", "Chumbivilcas" },
                { "0808", "08", "Espinar" },
                { "0809", "08", "La Convención" },
                { "0810", "08", "Paruro" },
                { "0811", "08", "Paucartambo" },
                { "0812", "08", "Quispicanchi" },
                { "0813", "08", "Urubamba" },
                { "0901", "09", "Huancavelica" },
                { "0902", "09", "Acobamba" },
                { "0903", "09", "Angaraes" },
                { "0904", "09", "Castrovirreyna" },
                { "0905", "09", "Churcampa" },
                { "0906", "09", "Huaytará" },
                { "0907", "09", "Tayacaja" },
                { "1001", "10", "Huánuco" },
                { "1002", "10", "Ambo" },
                { "1003", "10", "Dos de Mayo" },
                { "1004", "10", "Huacaybamba" },
                { "1005", "10", "Huamalíes" },
                { "1006", "10", "Leoncio Prado" },
                { "1007", "10", "Marañón" },
                { "1008", "10", "Pachitea" },
                { "1009", "10", "Puerto Inca" },
                { "1010", "10", "Lauricocha" },
                { "1011", "10", "Yarowilca" },
                { "1101", "11", "Ica" },
                { "1102", "11", "Chincha" },
                { "1103", "11", "Nasca" },
                { "1104", "11", "Palpa" },
                { "1105", "11", "Pisco" },
                { "1201", "12", "Huancayo" },
                { "1202", "12", "Concepción" },
                { "1203", "12", "Chanchamayo" },
                { "1204", "12", "Jauja" },
                { "1205", "12", "Junín" },
                { "1206", "12", "Satipo" },
                { "1207", "12", "Tarma" },
                { "1208", "12", "Yauli" },
                { "1209", "12", "Chupaca" },
                { "1301", "13", "Trujillo" },
                { "1302", "13", "Ascope" },
                { "1303", "13", "Bolívar" },
                { "1304", "13", "Chepén" },
                { "1305", "13", "Julcán" },
                { "1306", "13", "Otuzco" },
                { "1307", "13", "Pacasmayo" },
                { "1308", "13", "Pataz" },
                { "1309", "13", "Sánchez Carrión" },
                { "1310", "13", "Santiago de Chuco" },
                { "1311", "13", "Gran Chimú" },
                { "1312", "13", "Virú" },
                { "1401", "14", "Chiclayo" },
                { "1402", "14", "Ferreñafe" },
                { "1403", "14", "Lambayeque" },
                { "1501", "15", "Lima" },
                { "1502", "15", "Barranca" },
                { "1503", "15", "Cajatambo" },
                { "1504", "15", "Canta" },
                { "1505", "15", "Cañete" },
                { "1506", "15", "Huaral" },
                { "1507", "15", "Huarochirí" },
                { "1508", "15", "Huaura" },
                { "1509", "15", "Oyón" },
                { "1510", "15", "Yauyos" },
                { "1601", "16", "Maynas" },
                { "1602", "16", "Alto Amazonas" },
                { "1603", "16", "Loreto" },
                { "1604", "16", "Mariscal Ramón Castilla" },
                { "1605", "16", "Requena" },
                { "1606", "16", "Ucayali" },
                { "1607", "16", "Datem del Marañón" },
                { "1608", "16", "Putumayo" },
                { "1701", "17", "Tambopata" },
                { "1702", "17", "Manu" },
                { "1703", "17", "Tahuamanu" },
                { "1801", "18", "Mariscal Nieto" },
                { "1802", "18", "General Sánchez Cerro" },
                { "1803", "18", "Ilo" },
                { "1901", "19", "Pasco" },
                { "1902", "19", "Daniel Alcides Carrión" },
                { "1903", "19", "Oxapampa" },
                { "2001", "20", "Piura" },
                { "2002", "20", "Ayabaca" },
                { "2003", "20", "Huancabamba" },
                { "2004", "20", "Morropón" },
                { "2005", "20", "Paita" },
                { "2006", "20", "Sullana" },
                { "2007", "20", "Talara" },
                { "2008", "20", "Sechura" },
                { "2101", "21", "Puno" },
                { "2102", "21", "Azángaro" },
                { "2103", "21", "Carabaya" },
                { "2104", "21", "Chucuito" },
                { "2105", "21", "El Collao" },
                { "2106", "21", "Huancané" },
                { "2107", "21", "Lampa" },
                { "2108", "21", "Melgar" },
                { "2109", "21", "Moho" },
                { "2110", "21", "San Antonio de Putina" },
                { "2111", "21", "San Román" },
                { "2112", "21", "Sandia" },
                { "2113", "21", "Yunguyo" },
                { "2201", "22", "Moyobamba" },
                { "2202", "22", "Bellavista" },
                { "2203", "22", "El Dorado" },
                { "2204", "22", "Huallaga" },
                { "2205", "22", "Lamas" },
                { "2206", "22", "Mariscal Cáceres" },
                { "2207", "22", "Picota" },
                { "2208", "22", "Rioja" },
                { "2209", "22", "San Martín" },
                { "2210", "22", "Tocache" },
                { "2301", "23", "Tacna" },
                { "2302", "23", "Candarave" },
                { "2303", "23", "Jorge Basadre" },
                { "2304", "23", "Tarata" },
                { "2401", "24", "Tumbes" },
                { "2402", "24", "Contralmirante Villar" },
                { "2403", "24", "Zarumilla" },
                { "2501", "25", "Coronel Portillo" },
                { "2502", "25", "Atalaya" },
                { "2503", "25", "Padre Abad" },
                { "2504", "25", "Purús" }
        };

        List<Provincia> provincias = new ArrayList<>();
        for (String[] row : data) {
            Departamento departamento = departamentoRepository.findById(row[1]).orElse(null);
            if (departamento != null) {
                provincias.add(new Provincia(row[0], departamento, null, row[2], true));
            } else {
                log.warn("Departamento no encontrado para provincia {}: {}", row[0], row[1]);
            }
        }
        for (Provincia provincia : provincias) {
            try {
                provinciaRepository.save(provincia);
                log.info("✓ Provincia creada: {} - {} - {}", provincia.getId(), provincia.getDepartamento().getId(),
                        provincia.getNombre());
            } catch (Exception e) {
                log.error("Error al crear provincia {}: {}", provincia.getNombre(), e.getMessage());
            }
        }

        log.info("Provincias de demostración creadas exitosamente. Total: {}", provinciaRepository.count());
    }
}
