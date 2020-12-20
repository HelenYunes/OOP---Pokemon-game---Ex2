package test;

import static org.junit.jupiter.api.Assertions.*;


import api.DWGraph_DS;
import gameClient.Agent;
import gameClient.Arena;
import org.junit.jupiter.api.Test;


class AgentTest {
    Agent testAgent;
    DWGraph_DS testGraph;


    @Test
    void update() {
        agentSetUp();
        assertEquals(0, testAgent.getID());
        assertEquals(0.0, testAgent.getValue());
        assertEquals(4, testAgent.getSrcNode());
        assertEquals(-1, testAgent.getNextNode());
        assertEquals(1.0, testAgent.getSpeed());
    }

    public void agentSetUp() {
        testGraph = Arena.json2Graph("{\"Edges\":[{\"src\":0,\"w\":1.0286816758196655,\"dest\":1},{\"src\":0,\"w\":1.4195069847291193,\"dest\":2},{\"src\":0,\"w\":0.9046810940028001,\"dest\":8},{\"src\":0,\"w\":1.350112459863696,\"dest\":9},{\"src\":1,\"w\":1.0181033188594921,\"dest\":0},{\"src\":1,\"w\":1.9597400789729786,\"dest\":2},{\"src\":1,\"w\":0.922292376645709,\"dest\":8},{\"src\":1,\"w\":0.3036488046651732,\"dest\":9},{\"src\":1,\"w\":0.9029742168439744,\"dest\":10},{\"src\":2,\"w\":1.6233640426666327,\"dest\":0},{\"src\":2,\"w\":1.1196068362092921,\"dest\":1},{\"src\":2,\"w\":1.047600284711248,\"dest\":3},{\"src\":2,\"w\":0.9484993508901526,\"dest\":9},{\"src\":3,\"w\":1.6404807737481097,\"dest\":2},{\"src\":3,\"w\":1.3762856877574432,\"dest\":4},{\"src\":3,\"w\":1.6144436740062582,\"dest\":12},{\"src\":3,\"w\":1.2054023793722415,\"dest\":13},{\"src\":4,\"w\":1.0325537061811674,\"dest\":3},{\"src\":4,\"w\":1.8112664253321413,\"dest\":5},{\"src\":4,\"w\":0.8688885415846178,\"dest\":13},{\"src\":5,\"w\":1.741663365681239,\"dest\":4},{\"src\":5,\"w\":1.7741153504040086,\"dest\":6},{\"src\":5,\"w\":1.538260782642467,\"dest\":13},{\"src\":6,\"w\":1.9849068329328654,\"dest\":5},{\"src\":6,\"w\":1.527985551910803,\"dest\":7},{\"src\":6,\"w\":1.5123170334795832,\"dest\":11},{\"src\":7,\"w\":1.157482212671595,\"dest\":6},{\"src\":7,\"w\":1.3327366105160003,\"dest\":8},{\"src\":7,\"w\":1.069288137885289,\"dest\":10},{\"src\":7,\"w\":1.5833907507569327,\"dest\":11},{\"src\":8,\"w\":0.978973338700678,\"dest\":0},{\"src\":8,\"w\":1.0075048224085617,\"dest\":1},{\"src\":8,\"w\":1.1451676165794495,\"dest\":7},{\"src\":8,\"w\":1.5294982862903077,\"dest\":9},{\"src\":8,\"w\":1.3992718791113394,\"dest\":10},{\"src\":9,\"w\":1.5169870684820053,\"dest\":0},{\"src\":9,\"w\":0.5391252787683732,\"dest\":1},{\"src\":9,\"w\":1.0193642059466665,\"dest\":2},{\"src\":9,\"w\":1.6547197089377548,\"dest\":8},{\"src\":9,\"w\":1.3165977169718088,\"dest\":10},{\"src\":9,\"w\":1.3018172915854134,\"dest\":11},{\"src\":10,\"w\":0.8578002847689269,\"dest\":1},{\"src\":10,\"w\":1.1592060632152783,\"dest\":7},{\"src\":10,\"w\":1.3311874615637327,\"dest\":8},{\"src\":10,\"w\":1.501745227535202,\"dest\":9},{\"src\":10,\"w\":1.1502834534021935,\"dest\":11},{\"src\":10,\"w\":1.22989430243928,\"dest\":12},{\"src\":11,\"w\":1.5449135668700011,\"dest\":6},{\"src\":11,\"w\":1.473766674475559,\"dest\":7},{\"src\":11,\"w\":0.9612526722520012,\"dest\":9},{\"src\":11,\"w\":1.838839221439318,\"dest\":10},{\"src\":11,\"w\":1.7970499419188757,\"dest\":12},{\"src\":11,\"w\":1.21984879336453,\"dest\":13},{\"src\":12,\"w\":0.9248020107816467,\"dest\":3},{\"src\":12,\"w\":1.4227037805774765,\"dest\":10},{\"src\":12,\"w\":1.0782611398160369,\"dest\":11},{\"src\":12,\"w\":1.5820735518986457,\"dest\":13},{\"src\":13,\"w\":1.0622048713208108,\"dest\":3},{\"src\":13,\"w\":0.8625859471230286,\"dest\":4},{\"src\":13,\"w\":1.0131889452602725,\"dest\":5},{\"src\":13,\"w\":1.118472924808075,\"dest\":11},{\"src\":13,\"w\":1.8634366911523141,\"dest\":12},{\"src\":13,\"w\":1.591436701981711,\"dest\":14},{\"src\":14,\"w\":1.1624037310803381,\"dest\":38},{\"src\":14,\"w\":0.8881142417799636,\"dest\":28},{\"src\":14,\"w\":1.420433846016373,\"dest\":13},{\"src\":14,\"w\":0.5144256235463494,\"dest\":29},{\"src\":14,\"w\":1.4969695355104846,\"dest\":15},{\"src\":15,\"w\":1.820133518346724,\"dest\":16},{\"src\":15,\"w\":0.6830611418562124,\"dest\":39},{\"src\":15,\"w\":1.0571048134347765,\"dest\":27},{\"src\":15,\"w\":1.2539493948301978,\"dest\":14},{\"src\":16,\"w\":1.4751299479382745,\"dest\":17},{\"src\":16,\"w\":1.0652505692760776,\"dest\":25},{\"src\":16,\"w\":1.849559706791502,\"dest\":15},{\"src\":17,\"w\":1.8976550153516096,\"dest\":16},{\"src\":17,\"w\":1.476217280387361,\"dest\":18},{\"src\":17,\"w\":1.5221952669208463,\"dest\":25},{\"src\":18,\"w\":1.3812553716595573,\"dest\":17},{\"src\":18,\"w\":1.2288771708162884,\"dest\":19},{\"src\":19,\"w\":1.7385883632086174,\"dest\":18},{\"src\":19,\"w\":1.2462873901456768,\"dest\":20},{\"src\":19,\"w\":1.19196617015616,\"dest\":22},{\"src\":20,\"w\":1.0769170576729628,\"dest\":19},{\"src\":20,\"w\":1.5323428393221055,\"dest\":21},{\"src\":21,\"w\":0.25862974484668677,\"dest\":32},{\"src\":21,\"w\":0.4303080890843676,\"dest\":33},{\"src\":21,\"w\":1.4221361625367788,\"dest\":20},{\"src\":21,\"w\":1.8906745074897788,\"dest\":22},{\"src\":22,\"w\":1.4664843451799783,\"dest\":34},{\"src\":22,\"w\":1.1881244419302595,\"dest\":19},{\"src\":22,\"w\":1.216418520326696,\"dest\":21},{\"src\":22,\"w\":1.7540194603765131,\"dest\":23},{\"src\":23,\"w\":1.4048522751270207,\"dest\":22},{\"src\":23,\"w\":1.6207265076534472,\"dest\":24},{\"src\":23,\"w\":1.0637618251902718,\"dest\":31},{\"src\":24,\"w\":1.1841063098707765,\"dest\":23},{\"src\":24,\"w\":1.323585364227073,\"dest\":25},{\"src\":24,\"w\":0.8220347722058235,\"dest\":31},{\"src\":25,\"w\":0.9988225469223265,\"dest\":16},{\"src\":25,\"w\":0.9396159841598516,\"dest\":17},{\"src\":25,\"w\":1.4562642948651194,\"dest\":24},{\"src\":25,\"w\":1.4755946792948635,\"dest\":26},{\"src\":26,\"w\":1.5467483087385425,\"dest\":25},{\"src\":26,\"w\":1.7412081979582206,\"dest\":27},{\"src\":26,\"w\":1.5047795086351257,\"dest\":30},{\"src\":27,\"w\":1.52938931885858,\"dest\":26},{\"src\":27,\"w\":1.4543534256813453,\"dest\":28},{\"src\":27,\"w\":0.9536046338092429,\"dest\":29},{\"src\":27,\"w\":0.7107048585807265,\"dest\":15},{\"src\":28,\"w\":1.36227998284734,\"dest\":27},{\"src\":28,\"w\":1.0275874254342312,\"dest\":29},{\"src\":28,\"w\":0.6930635766320317,\"dest\":14},{\"src\":29,\"w\":1.4651527753379332,\"dest\":38},{\"src\":29,\"w\":1.4070251370265445,\"dest\":27},{\"src\":29,\"w\":1.7292718910955136,\"dest\":28},{\"src\":29,\"w\":0.4049076043381138,\"dest\":14},{\"src\":29,\"w\":1.7909921211692286,\"dest\":30},{\"src\":30,\"w\":1.7344187759639182,\"dest\":26},{\"src\":30,\"w\":1.7136443487110584,\"dest\":29},{\"src\":30,\"w\":1.236460311353157,\"dest\":31},{\"src\":31,\"w\":1.3390789447053093,\"dest\":32},{\"src\":31,\"w\":0.982689589007769,\"dest\":36},{\"src\":31,\"w\":1.1493968658163505,\"dest\":23},{\"src\":31,\"w\":1.1564501886545782,\"dest\":24},{\"src\":31,\"w\":1.887827531657794,\"dest\":30},{\"src\":32,\"w\":1.0699930480801485,\"dest\":33},{\"src\":32,\"w\":0.2501423643989235,\"dest\":21},{\"src\":32,\"w\":1.59014224465793,\"dest\":31},{\"src\":33,\"w\":1.5073810551411844,\"dest\":32},{\"src\":33,\"w\":1.0859863153695013,\"dest\":34},{\"src\":33,\"w\":0.5467923517907014,\"dest\":21},{\"src\":34,\"w\":1.8182715533786942,\"dest\":33},{\"src\":34,\"w\":1.1709935404178515,\"dest\":35},{\"src\":34,\"w\":1.18974585591212,\"dest\":22},{\"src\":35,\"w\":1.3112919927450934,\"dest\":34},{\"src\":35,\"w\":1.1582862186800131,\"dest\":36},{\"src\":36,\"w\":1.4084797549223125,\"dest\":35},{\"src\":36,\"w\":1.5690172998578196,\"dest\":37},{\"src\":36,\"w\":1.3237931484870435,\"dest\":31},{\"src\":37,\"w\":1.4424323460518078,\"dest\":36},{\"src\":37,\"w\":1.3207349411381886,\"dest\":38},{\"src\":38,\"w\":1.0885502857669094,\"dest\":37},{\"src\":38,\"w\":1.4633161212399912,\"dest\":39},{\"src\":38,\"w\":1.5594567865076616,\"dest\":29},{\"src\":38,\"w\":1.4492999852158956,\"dest\":14},{\"src\":39,\"w\":1.6299895522533827,\"dest\":38},{\"src\":39,\"w\":1.058991898489301,\"dest\":40},{\"src\":39,\"w\":0.8816461149812365,\"dest\":15},{\"src\":40,\"w\":1.1634517513453932,\"dest\":39},{\"src\":40,\"w\":1.8844002953775134,\"dest\":41},{\"src\":41,\"w\":1.6234430136750946,\"dest\":40},{\"src\":41,\"w\":1.2686775729345499,\"dest\":42},{\"src\":42,\"w\":1.6919185227049218,\"dest\":41},{\"src\":42,\"w\":1.102809825187329,\"dest\":43},{\"src\":43,\"w\":1.4286755773931743,\"dest\":42},{\"src\":43,\"w\":1.381453826569428,\"dest\":44},{\"src\":44,\"w\":1.991272093414738,\"dest\":43},{\"src\":44,\"w\":1.2177924643456808,\"dest\":45},{\"src\":44,\"w\":1.9231294994478854,\"dest\":46},{\"src\":45,\"w\":1.2808847437539053,\"dest\":44},{\"src\":45,\"w\":1.5768016138579601,\"dest\":46},{\"src\":46,\"w\":1.7870260280212817,\"dest\":44},{\"src\":46,\"w\":1.580936966996373,\"dest\":45},{\"src\":46,\"w\":1.0830397601476496,\"dest\":47},{\"src\":47,\"w\":1.126130739877016,\"dest\":46}],\"Nodes\":[{\"pos\":\"35.212217299435025,32.106235628571426,0.0\",\"id\":0},{\"pos\":\"35.21172908313156,32.10509072268908,0.0\",\"id\":1},{\"pos\":\"35.21313005165456,32.1046000487395,0.0\",\"id\":2},{\"pos\":\"35.211092279257464,32.10265552605042,0.0\",\"id\":3},{\"pos\":\"35.21015830024213,32.101110811764705,0.0\",\"id\":4},{\"pos\":\"35.20754740435835,32.10254648739496,0.0\",\"id\":5},{\"pos\":\"35.207993167070214,32.10467274117647,0.0\",\"id\":6},{\"pos\":\"35.20945781598063,32.105781300840334,0.0\",\"id\":7},{\"pos\":\"35.210540382566585,32.106235628571426,0.0\",\"id\":8},{\"pos\":\"35.211283320419696,32.10476360672269,0.0\",\"id\":9},{\"pos\":\"35.21034934140436,32.104654568067225,0.0\",\"id\":10},{\"pos\":\"35.20964885714286,32.104091201680674,0.0\",\"id\":11},{\"pos\":\"35.20960640355125,32.103364277310924,0.0\",\"id\":12},{\"pos\":\"35.209415362389024,32.10265552605042,0.0\",\"id\":13},{\"pos\":\"35.203259591606134,32.1031462,0.0\",\"id\":14},{\"pos\":\"35.201731262308314,32.105963031932774,0.0\",\"id\":15},{\"pos\":\"35.19895055205811,32.10574495462185,0.0\",\"id\":16},{\"pos\":\"35.197379769168684,32.10534514621849,0.0\",\"id\":17},{\"pos\":\"35.193304224374494,32.10634466722689,0.0\",\"id\":18},{\"pos\":\"35.19256128652139,32.104563702521006,0.0\",\"id\":19},{\"pos\":\"35.18944094753834,32.10514524201681,0.0\",\"id\":20},{\"pos\":\"35.18763666989508,32.10383677815126,0.0\",\"id\":21},{\"pos\":\"35.19181834866828,32.103055334453785,0.0\",\"id\":22},{\"pos\":\"35.19623352219532,32.1012743697479,0.0\",\"id\":23},{\"pos\":\"35.19797411945117,32.102764564705886,0.0\",\"id\":24},{\"pos\":\"35.19858969652946,32.10429110588235,0.0\",\"id\":25},{\"pos\":\"35.200181706214686,32.1041457210084,0.0\",\"id\":26},{\"pos\":\"35.201752489104116,32.104636394957986,0.0\",\"id\":27},{\"pos\":\"35.2023256125908,32.10354600840336,0.0\",\"id\":28},{\"pos\":\"35.202728921711056,32.10312802689076,0.0\",\"id\":29},{\"pos\":\"35.200160479418884,32.10240110252101,0.0\",\"id\":30},{\"pos\":\"35.19786798547216,32.10151062016807,0.0\",\"id\":31},{\"pos\":\"35.18736072154964,32.10374591260504,0.0\",\"id\":32},{\"pos\":\"35.18795507183212,32.10330975798319,0.0\",\"id\":33},{\"pos\":\"35.19077823567393,32.10136523529412,0.0\",\"id\":34},{\"pos\":\"35.19398348184019,32.10071100336135,0.0\",\"id\":35},{\"pos\":\"35.19943876836158,32.10038388739496,0.0\",\"id\":36},{\"pos\":\"35.20294118966909,32.09925715462185,0.0\",\"id\":37},{\"pos\":\"35.20391762227603,32.10154696638656,0.0\",\"id\":38},{\"pos\":\"35.20192230347054,32.10710793781513,0.0\",\"id\":39},{\"pos\":\"35.19007775141243,32.107998420168066,0.0\",\"id\":40},{\"pos\":\"35.19918404681194,32.109397749579834,0.0\",\"id\":41},{\"pos\":\"35.20033029378531,32.10919784537815,0.0\",\"id\":42},{\"pos\":\"35.20364167393059,32.109325057142854,0.0\",\"id\":43},{\"pos\":\"35.204257251008876,32.10852544033614,0.0\",\"id\":44},{\"pos\":\"35.20500018886199,32.10785303529412,0.0\",\"id\":45},{\"pos\":\"35.204957735270376,32.106689956302525,0.0\",\"id\":46},{\"pos\":\"35.204809147699756,32.105490531092435,0.0\",\"id\":47}]}");
        testAgent = new Agent(testGraph, 39);
        String agent = "{\n" +
                "   \"Agent\":{\n" +
                "      \"id\":0,\n" +
                "      \"value\":0.0,\n" +
                "      \"src\":4,\n" +
                "      \"dest\":-1,\n" +
                "      \"speed\":1.0,\n" +
                "      \"pos\":\"35.2016888087167,32.10601755126051,0.0\"\n" +
                "   }\n" +
                "}";

        testAgent.updateAgents(agent);
    }

    @Test
    void toJSON() {
        agentSetUp();
        String agent = testAgent.toJSON();
        String agent2 = "{\"Agent\":{\"id\":0,\"value\":0.0,\"src\":4,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.2016888087167,32.10601755126051,0.0\"}}";
        assertEquals(agent, agent2);

        agent2 = "{\"Agent\":{\"id\":0,\"value\":0.0,\"src\":4,\"dest\":-1,\"speed\":1.0,\"pos\":\"34.2016888087167,32.10601755126051,0.0\"}}";
        assertNotEquals(agent, agent2);
    }
}
