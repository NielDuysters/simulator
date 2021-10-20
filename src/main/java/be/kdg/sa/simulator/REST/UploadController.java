package be.kdg.sa.simulator.REST;

import be.kdg.sa.simulator.Messaging.RabbitSender;
import be.kdg.sa.simulator.domain.CSVLine;
import be.kdg.sa.simulator.domain.LineType;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {

    private RabbitSender rabbitSender;

    private List<CSVLine> csvContents;
    private List<CSVLine> flightInfoLines = new ArrayList<CSVLine>();
    private List<CSVLine> cargoInfoLines = new ArrayList<CSVLine>();
    private final RestClient restClient;


    public UploadController(RabbitSender rabbitSender, RestClient restClient) {
        this.rabbitSender = rabbitSender;
        this.restClient = restClient;
    }

    @GetMapping("/upload")
    public ModelAndView uploadPage() {
        var mav = new ModelAndView();
        mav.setViewName("upload");

        return mav;
    }

    @SneakyThrows
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Dit bestand was leeg...");
            return "upload";
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<CSVLine> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVLine.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            csvContents = csvToBean.parse();

            // Temp outputting
            for (CSVLine line : csvContents) {
                System.out.println(line.toString());
            }

            // Separating FlightInfo- and CargoInfo-lines
            for (CSVLine line : csvContents) {
                if (line.getType() == LineType.FlightInfo) {
                    flightInfoLines.add(line);
                }
                if (line.getType() == LineType.CargoInfo) {
                    cargoInfoLines.add(line);
                }
            }

            // Send flightmessages
            for (CSVLine flightInfo : flightInfoLines) {
                Gson gson = new Gson();
                String json_string = gson.toJson(flightInfo);

                //restClient.createFlight(flightInfo.toFlight());
                restClient.createFlight();

                //rabbitSender.send("fm_queue", json_string);
            }

            // Delay 10 secs
            //Log.d("Delaying 10 secs");
            Thread.sleep(10000);

            // Send cargomessages
            for (CSVLine cargoInfo : cargoInfoLines) {
                Gson gson = new Gson();
                String json_string = gson.toJson(cargoInfo);



                //rabbitSender.send("cg_queue", json_string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("uploadStatus", "Uploaden gelukt...");

        return "upload";
    }
}
