package de.neebs.indexing.controller;

import de.neebs.indexing.model.event.Distribution;
import de.neebs.indexing.model.event.DistributionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/edi")
@RestController
public class EdiImportController {
    private static final Logger LOG = LoggerFactory.getLogger(EdiImportController.class);

    @Autowired
    private DistributionRepository distributionRepository;

    @RequestMapping(value = "/680", method = RequestMethod.GET)
    public ResponseEntity<?> read680() {
        InputStream is = getClass().getResourceAsStream("/20190130_680.txt");
        Scanner scanner = new Scanner(is);
        if (!scanner.hasNextLine()) {
            return ResponseEntity.badRequest().build();
        }
        scanner.nextLine();
        if (!scanner.hasNextLine()) {
            return ResponseEntity.badRequest().build();
        }
        List<String> headline = Arrays.asList(scanner.nextLine().split("\t"));
        while (scanner.hasNextLine()) {
            try {
                List<String> line = Arrays.asList(scanner.nextLine().split("\t"));
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headline.size(); i++) {
                    if (line.size() - 1 >= i)
                    map.put(headline.get(i), line.get(i));
                }
                if ("DIV".equals(map.get("eventcd"))) {
                    String isin = map.get("isin");
                    String payDate = map.get("date3");
                    String rate1 = map.get("rate1");
                    String rate2 = map.get("rate2");
                    Float netDistributionRate;
                    if (rate2 != null && !"".equals(rate2)) {
                        netDistributionRate = Float.parseFloat(rate2);
                    } else if (rate1 != null && !"".equals(rate1)){
                        netDistributionRate = Float.parseFloat(rate1);
                    } else {
                        netDistributionRate = null;
                    }
                    if (netDistributionRate == null || payDate == null || "".equals(payDate) || isin == null || "".equals(isin)) {
                        LOG.error("Missing parameters: netDistributionRate = " + netDistributionRate + "; payDate = " + payDate + "; isin = " + isin);
                    } else {
                        System.out.println("DIVIDEND for " + isin);
                        Date effectiveDate = new SimpleDateFormat("yyyy/MM/dd").parse(payDate);
                        Optional<Distribution> optionalDistribution = distributionRepository.findByIsinAndEffectiveDate(isin, effectiveDate);
                        if (optionalDistribution.isPresent()) {
                            Distribution distribution = optionalDistribution.get();
                            if (distribution.getNetDistributionRate() != netDistributionRate) {
                                LOG.warn("Other distribution rate found than stored: " + distribution.getNetDistributionRate() + ", found: " + netDistributionRate);
                            }
                        } else {
                            Distribution distribution = new Distribution(isin, effectiveDate, netDistributionRate);
                            distribution = distributionRepository.save(distribution);
                        }
                    }
                }
            } catch (ParseException e) {
                LOG.error("Cannot parse date", e);
            }
        }
        scanner.close();
        return ResponseEntity.ok("success");
    }

}
