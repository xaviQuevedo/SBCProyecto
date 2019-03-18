package sbc;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.rdf.model.RDFWriter;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;

public class SBC {

    public static void main(String[] args) throws FileNotFoundException {

        Model model = ModelFactory.createDefaultModel();
        //ruta donde se va a aguardar el modelo
        File f = new File("/home/xavier/NetBeansProjects/SBC/proyectmodelo.rdf");
        FileOutputStream os = new FileOutputStream(f);

        BufferedReader br1 = null;
        BufferedReader br2 = null;

        Resource WrittenWork = null;
        Resource Person = null;
        Resource Article = null;
        Resource Magazine = null;
        Resource Database = null;
        Resource Project = null;

        //Fijar Prefijo para URI base de dos datos a crear 
        String dataPrefix = "http://example.org/data/";
        model.setNsPrefix("myData", dataPrefix);

        //Fijar prefijos de vocabularios incorporados en Jena
        String foaf = "http://xmlns.com/foaf/0.1/";
        model.setNsPrefix("foaf", foaf);

        String dbo = "http://dbpedia.org/ontology/";
        model.setNsPrefix("dbo", dbo);
        Model dboModel = ModelFactory.createDefaultModel(); // MODELO PARA LA ONTOLOGIA
        dboModel.read(dbo);
        /*
         String owl = "http://www.w3.org/2002/07/owl#";
         model.setNsPrefix("owl", owl);
         */
        Model voc = ModelFactory.createDefaultModel();  // modelo para la ontolog√≠a
        dboModel.read(dbo);

        Model dimension = ModelFactory.createDefaultModel();  // modelo para la ontolog√≠a
        dboModel.read(dbo);

        Model enrolmentdata = ModelFactory.createDefaultModel();  // modelo para la ontolog√≠a
        dboModel.read(dbo);

        Model eduactionfields = ModelFactory.createDefaultModel();  // modelo para la ontolog√≠a
        dboModel.read(dbo);

        Model hasData = ModelFactory.createDefaultModel();  // modelo para la ontolog√≠a
        dboModel.read(dbo);

        Model inDataset = ModelFactory.createDefaultModel();  // modelo para la ontolog√≠a
        dboModel.read(dbo);

        //-------------------------------
        //Importar y leer el model creado en protege
        OntModel myModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        //myModel.write(System.out, "RDF/XML");
        //myModel.read("C:/Users/Jose Guarnizo/Desktop/NuevoSBC.owl", "RDF/XML");

        try {

            //DATA ESTADISTICAS MATRICULADOS
            br1 = new BufferedReader(new FileReader("/home/xavier/NetBeansProjects/SBC/Person_data.csv"));
           // br2 = new BufferedReader(new FileReader("/home/xavier/NetBeansProjects/SBC/proyectos_data.csv"));
            System.out.println("llego");
            String line1;
            br1.readLine();

            while ((line1 = br1.readLine()) != null && (line1 = br2.readLine()) != null) {
                //System.out.println("g" + line1 + "gg" + br1 + "ggg" + br2 + "gggg");
                String[] data1 = line1.split(";");
                String perso = data1[0].replaceAll("\\s+", "").replaceAll("\"", " ");
                System.out.println("g" + line1 + "g");
                // String[] data2 = line1.split(";");
                String art = "Article";
                String prsn = "Person";
                String ww = "WrittenWork";
                String mgzn = "Magazine";
                String db = "Database";
                String prjt = "Project";

                Person = model.createResource(dataPrefix + prsn)
                        .addProperty(RDF.type, (foaf + "Person"))
                        .addProperty(FOAF.name, data1[5])
                        .addProperty(dboModel.getProperty(dbo + "documentIdentification"), model.createTypedLiteral(data1[10], XSDDatatype.XSD));

//                WrittenWork = model.createResource(dataPrefix + ww)
//                        .addProperty(RDF.type, (dbo + "WrittenWork"))
//                        .addProperty(FOAF.name, data1[5])
//                        .addProperty(dboModel.getProperty(dbo + "firstPublicationDate"), model.createTypedLiteral(data1[7], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "year"), model.createTypedLiteral(data1[3], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "abstract"), model.createTypedLiteral(data1[1], XSDDatatype.XSD));
//
//                Article = model.createResource(dataPrefix + art)
//                        .addProperty(RDF.type, (dbo + "Article"))
//                        .addProperty(dboModel.getProperty(dbo + "url"), model.createTypedLiteral(data1[9], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "status"), model.createTypedLiteral(data1[6], XSDDatatype.XSD));
//                Magazine = model.createResource(dataPrefix + mgzn)
//                        .addProperty(RDF.type, (dbo + "Magazine"))
//                        .addProperty(dboModel.getProperty(dbo + "isbn"), model.createTypedLiteral(data1[0], XSDDatatype.XSD))
//                        .addProperty(FOAF.name, data1[8]);
//                Database = model.createResource(dataPrefix + db)
//                        .addProperty(RDF.type, (dbo + "Database"))
//                        .addProperty(FOAF.name, data1[4]);
//                Project = model.createResource(dataPrefix + prjt)
//                        .addProperty(RDF.type, (dbo + "Project"))
//                        .addProperty(FOAF.name, data2[3])
//                        .addProperty(dboModel.getProperty(dbo + "year"), model.createTypedLiteral(data2[9], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "code"), model.createTypedLiteral(data2[1], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "status"), model.createTypedLiteral(data2[0], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "comment"), model.createTypedLiteral(data2[2], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "projectEndDate"), model.createTypedLiteral(data2[10], XSDDatatype.XSD))
//                        .addProperty(dboModel.getProperty(dbo + "projectStartDate"), model.createTypedLiteral(data2[4], XSDDatatype.XSD));
            }
//            model.add(WrittenWork, myModel.getProperty(dbo + "author"), Person);
//             model.add(Project, myModel.getProperty(dbo + "Project"), Person);
//            model.add(Article, RDFS.subClassOf, WrittenWork);
//             model.add(Project, RDFS.subClassOf, WrittenWork);
//            model.add(Article, myModel.getProperty(dbo + "magazine"), Magazine);
//            model.add(Article, myModel.getProperty(dbo + "dataBase"), Database);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (br1 != null) {
                    br1.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object

            System.out.print("ggg" + subject.toString());
            System.out.print(" gggggggg" + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print("hhhhh \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }

        // now write the model in XML form to a file
        System.out.println("MODELO RDF ------");
        model.write(System.out, "RDF/XML");

        // Save to a file
        RDFWriter writer = model.getWriter("RDF/XML"); //RDF/XML
        writer.write(model, os, "");

        //Cerrar modelos
        dboModel.close();
        model.close();

    }

}
