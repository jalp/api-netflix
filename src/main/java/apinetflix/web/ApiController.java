package apinetflix.web;

import apinetflix.exception.ExceptionResponse;
import apinetflix.exception.UserException;
import apinetflix.pojo.Container;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    public List<Container> getSavedAds(@PathVariable("account_id") int account_id,
                                @RequestParam(value = "lim", required = false, defaultValue = "10") int lim,
                                @RequestParam(value = "o", required = false, defaultValue = "1") int offset) throws UserException {
        if (account_id == 0) {
            logger.error("User " + account_id + " does not exists");
            throw new UserException("User " + account_id + " does not exists");
        }

        logger.debug("getSavedAds with id " + account_id + ", lim: " + lim + ", offset: " + offset);
        List<Container> ad_list = new ArrayList<>();
        IntStream.range(0, lim).parallel().forEach(
                n -> {
                    Container container = new Container();
                    container.setCompanyAd(true);
                    container.setListId(n);
                    container.setBody("LoremIpsum");
                    container.setSubject("LoremIpsum subject");
                    ad_list.add(container);
                }
        );
        return ad_list;
    }

    @RequestMapping(value = "{account_id}/saved_ads", method = RequestMethod.POST)
    public String postSavedAds(@PathVariable("account_id") int account_id,
                               @RequestParam("list_id") String list_id,
                               @RequestHeader("X-NGA-SOURCE") String header,
                               @RequestBody Container container) {
        logger.info("PostSavedAds for " + account_id + " container list: " + list_id + " body " + container.toString());
        return "Saved " + account_id + " container list: " + list_id + " body " + container.toString();
    }

    @RequestMapping(value = "/{accountId}/container", method = RequestMethod.POST)
    public LinkedHashMap postContainer(@PathVariable("accountId") int accountId,
                                       @RequestBody Container container) {
        logger.info("Account id: " + accountId + " with content " + container);
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("result", "Data saved correctly");
        return response;
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(UserException uex) {
        return new ExceptionResponse(uex.getMessage());
    }
}