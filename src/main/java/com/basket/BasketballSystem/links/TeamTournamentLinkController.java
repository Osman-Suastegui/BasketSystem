package com.basket.BasketballSystem.links;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RequestMapping("/team-tournament-links")
@RestController
public class TeamTournamentLinkController {

    @Autowired
    TeamTournamentLinkService teamTournamentLink;
    @PostMapping("/generate/{teamId}/{tournamentId}")
    public ResponseEntity<String> generateInvite(@PathVariable Long teamId,@PathVariable Long tournamentId) {
        TeamTournamentLink link = teamTournamentLink.generateInvite(teamId,tournamentId);
        return ResponseEntity.ok(link.getToken());
    }

    @GetMapping("/getLink/{teamId}/{tournamentId}")
    public ResponseEntity<HashMap<String,String>> getLink(@PathVariable Long teamId,@PathVariable Long tournamentId) {
        TeamTournamentLink link = teamTournamentLink.getLink(teamId,tournamentId);
        HashMap<String,String> res = new HashMap<>();
        res.put("token", link.getToken());
        return ResponseEntity.ok(res);
    }

    // Validate the link by checking if the token is correct
    @GetMapping("/validate/{teamId}/{tournamentId}/{token}")
    public ResponseEntity<HashMap<String,String>> validateLink(@PathVariable Long teamId, @PathVariable Long tournamentId, @PathVariable String token) {
        boolean isValid = teamTournamentLink.validateLink(teamId, tournamentId, token);
        HashMap<String,String> res = new HashMap<>();
        res.put("message","Link is valid");

        if (!isValid) {
            res.replace("message","Invalid link");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        return ResponseEntity.ok(res);

    }


}
