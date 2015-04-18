package apinetflix.web;

import apinetflix.exception.ExceptionResponse;
import apinetflix.exception.UserException;
import apinetflix.pojo.Ad;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/account")
public class ApiController {

    private static final Logger logger = Logger.getLogger(ApiController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String pong() {
        logger.info("Ping called");
        return "pong";
    }

    @RequestMapping(value = "{account_id}/saved_ads", method = RequestMethod.GET)
    public List<Ad> getSavedAds(@PathVariable("account_id") int account_id,
                                @RequestParam(value = "lim", required = false, defaultValue = "10") int lim,
                                @RequestParam(value = "o", required = false, defaultValue = "1") int offset) throws UserException {
        if (account_id == 0){
            logger.error("User " + account_id + " does not exists");
            throw new UserException("User " + account_id + " does not exists");
        }

        logger.debug("getSavedAds with id " + account_id + ", lim: " + lim + ", offset: " + offset);
        List<Ad> ad_list = new ArrayList<>();
        IntStream.range(0, lim).parallel().forEach(
                n -> {
                    Ad ad = new Ad();
                    ad.setCompanyAd(true);
                    ad.setListId(n);
                    ad.setBody("LoremIpsum");
                    ad.setSubject("LoremIpsum subject");
                    ad_list.add(ad);
                }
        );
        return ad_list;
    }

    @RequestMapping(value = "{account_id}/saved_ads", method = RequestMethod.POST)
    public String postSavedAds(@PathVariable("account_id") int account_id,
                               @RequestParam("list_id") String list_id,
                               @RequestHeader("X-NGA-SOURCE") String header,
                               @RequestBody Ad ad) {
        logger.info("PostSavedAds for " + account_id + " ad list: " + list_id + " body " + ad.toString());
        return "Saved " + account_id + " ad list: " + list_id + " body "+ ad.toString() ;
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(UserException uex){
        return new ExceptionResponse(uex.getMessage());
    }
}