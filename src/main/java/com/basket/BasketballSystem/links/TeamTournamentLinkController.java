package com.basket.BasketballSystem.links;

import org.springframework.beans.factory.annotation.Autowired;
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
}
