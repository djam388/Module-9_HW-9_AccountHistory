import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String historyFilePath = "data/movementList.csv";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args)
    {
        ArrayList<MyAccount> accountHistory = getHistory();
        System.out.println("Total income:");
        accountHistory.stream().map(MyAccount::getIncome).reduce((income1, income2) -> income1 + income2).ifPresent(System.out::println);
        System.out.println("Total expense:");
        accountHistory.stream().map(MyAccount::getExpense).reduce((expense1, expense2) -> expense1 + expense2).ifPresent(System.out::println);

        //TreeSet historyDescription = getHistoryDescription(accountHistory);

        TreeSet historyDescription = new TreeSet();

        for (int i = 0; i < accountHistory.size(); i++)
        {
            historyDescription.add(accountHistory.get(i).getOperationDescription());
        }


        System.out.println();
        System.out.println("*Income*" + "\t\t\t" + "*Expense*" + "\t\t\t\t\t\t" +  "***Operation description***");

        for (int i = 0; i < historyDescription.size(); i++)
        {
            String textFilter = String.valueOf(historyDescription.toArray()[i]);

            accountHistory.stream().filter(dsc -> dsc.getOperationDescription().equals(textFilter))
                    .map(MyAccount::getIncome)
                    .reduce((income1, income2) -> income1 + income2).ifPresent(System.out::print);

            System.out.print("\t\t\t\t\t");

            accountHistory.stream().filter(dsc -> dsc.getOperationDescription().equals(textFilter))
                    .map(MyAccount::getExpense)
                    .reduce((expense1, expense2) -> expense1 + expense2).ifPresent(System.out::print);

            System.out.println("\t\t\t\t\t\t\t\t" + historyDescription.toArray()[i]);
        }

    }


    private static ArrayList<MyAccount> getHistory()
    {
        ArrayList<MyAccount> movements = new ArrayList<>();

        try {
            String fullDescription = "";
            List<String> lines = Files.readAllLines(Paths.get(historyFilePath));

            for(String line : lines)
            {

                String[] fragments = line.split(",");
                if (fragments[0].equals("Тип счёта"))
                {
                    continue;
                }
                if (fragments.length != 8)
                {
                    String regex = "\"\\d+,\\d+\"";
                    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(line);
                    String textToFormat = "";
                    String correctedText = "";

                    while (matcher.find())
                    {
                        textToFormat = matcher.group(0);
                        correctedText = textToFormat.replace("\"","");
                        correctedText = correctedText.replace(",",".");

                        line = line.substring(0,line.indexOf(textToFormat))
                                + correctedText
                                + line.substring(line.indexOf(textToFormat) + textToFormat.length());
                        //System.out.println(line);
                    }
                    fragments = line.split(",");
                }
                fullDescription = fragments[5].replace("\\", "/");
                fullDescription = fullDescription.substring(fullDescription.lastIndexOf("/") + 1);
                fullDescription = fullDescription.substring(0, fullDescription.indexOf("  "));
                movements.add(new MyAccount(
                        (new SimpleDateFormat(dateFormat)).parse(fragments[3]),
                        fullDescription,
                        Double.parseDouble(fragments[6]),
                        Double.parseDouble(fragments[7])
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movements;
    }
}
