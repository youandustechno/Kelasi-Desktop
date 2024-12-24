package ui

object LocalizedStrings {

    const val ADD_DURATION = "Add Duration"
    const val ALREADY_TAKEN = "AlreadyTaken"
    const val ANSWER = "Answer"
    const val ASSERTION = "Assertion"
    const val AUTH_CODE = "AuthCode"
    const val AUTHENTICATION_FAILURE = "Authentication failure"

    const val BEFORE_YOU_TAKE = "BeforeYouTake"

    const val CARD_NUMBER = "CardNumber"
    const val CLOSE = "CLOSE"
    const val CONFIRM_NEW_PASSWORD = "ConfirmNewPassword"
    const val CONFIRM_PASSWORD = "ConfirmPassword"
    const val CORRECT_ANSWER = "CorrectAnswer"
    const val COURSE_NAME = "CourseName"
    const val COURSE_DESCRIPTION = "CourseDescription"
    const val CVV = "CVV"

    const val DOCUMENTATION = "Documentation"
    const val DOCUMENTS= "Documents"

    const val EMAIL = "Email"
    const val EMAIL_ERROR = "EmailError"
    const val EMAIL_SAMPLE = "EmailSample"
    const val ENTER_PIN = "EnteredPIN"
    const val EXPIRATION_DATE = "ExpirationDate"

    const val FILE_NO_FOUND = "NoFound"
    const val FIRST_NAME = "FirstName"

    const val HOME = "Home"

    const val INSTITUTION = "Institution"
    const val INSTRUCTOR = "InstructorName"

    const val LAST_NAME = "LastName"
    const val LEVEL = "Level"
    const val LOADING = "Loading"
    const val LOGIN_FAILURE = "LoginFailure"
    const val LOGIN = "Login"
    const val LOGIN_HELP = "LoginHelp"
    const val LOGIN_TITLE = "LOGIN"
    const val LOGOUT = "Logout"

    const val MANAGE_QUIZZES = "ManageQuizzes"
    const val MANAGE_VIDEOS = "ManageVideos"
    const val MIDDLE_NAME = "MiddleName"
    const val MODULE_NAME = "ModuleName"

    const val NAME_ON_CARD = "NameOnCard"
    const val NEW_PASSWORD = "NewPassword"
    const val NO_GRADED_YET = "NoGraded"

    const val ORGANIZATION_NAME = "OrganizationName"

    const val PASSWORD = "Password"
    const val PASSWORD_ERROR = "PasswordError"
    const val PAY_WITH_PAYPAL = "PayWithPayPal"
    const val PERSONAL_INFO = "PersonalInfo"
    const val PHONE = "Phone"
    const val PHONE_ERROR = "PhoneError"
    const val PHONE_NUMBER = "PhoneNumber"

    const val QUESTION = "Question"
    const val QUIZ_NUMBER = "QuizNumber"
    const val QUIZ = "Quiz"
    const val QUIZ_RESULT = "QuizResult"
    const val QUIZZES = "Quizzes"

    const val REGISTER = "Register"

    const val SELECT_FILE = "SelectFile"
    const val SELECTED  = "Selected"
    const val SETTINGS  = "Settings"
    const val SORRY_FOR_INCONVENIENCE  = "SorryForInconvenience"
    const val SUBMIT_QUIZ  = "SubmitQuiz"
    const val SUBSCRIPTION_PAYMENT = "SubscriptionPayment"
    const val START_QUIZ  = "StartQuiz"

    const val UPDATE_BUTTON  = "UpdateButton"
    const val UPDATE_PICTURE  = "UpdatePic"
    const val USERNAME  = "username"

    const val VALIDATE = "Validate"
    const val VERIFICATION_CODE_KEY = "VerificationCodeKey"
    const val VERIFICATION_CODE_TITLE = "VerificationCodeTitle"
    const val VERIFY = "Verify"
    const val VIDEOS = "Videos"
    const val VIDEO_NO_FOUND = "NoVideoAvailable"
    const val VIDEO_DESCRIPTION = "VideoDescription"
    const val VIDEO_TITLE= "VideoTitle"

    const val WELCOME = "Welcome"
    const val WELCOME_TO_ASSESSMENT = "WelcomeAssessment"
    const val WRONG_CODE = "WrongCode"

    const val YOUR_ANSWER = "YourAnswer"

    val en = mapOf(
        //A
        ADD_DURATION to "Add Duration",
        ALREADY_TAKEN to "Already taken",
        ASSERTION to "Assertion",
        ANSWER to "Answer",
        AUTH_CODE to "Auth Code",
        //B
        BEFORE_YOU_TAKE to "Before you start this quiz. Make sure you have stable internet connection " +
                "and once you click on continue, you can not go off this screen or close it before you submit it." +
                "Failing to do so, you will get the lowest mark for this quiz",
        //C
        CARD_NUMBER to "Card Number",
        CLOSE to "CLOSE",
        CONFIRM_PASSWORD to "Confirm Password",
        CONFIRM_NEW_PASSWORD to "Confirm New Password",
        CORRECT_ANSWER  to "Correct answer",
        COURSE_NAME  to "Course Name",
        COURSE_DESCRIPTION  to "Course Description",
        CVV  to "CVV",
        //D
        DOCUMENTATION  to "Documentation",
        DOCUMENTS  to "Documents",
        //E
        EMAIL to "Email",
        EMAIL_ERROR to "The value you entered does not match the email format address@domain.extension",
        EMAIL_SAMPLE to "address@domain.extension",
        ENTER_PIN  to "ENTER PIN",
        EXPIRATION_DATE  to "Expiration Date (MM/YY)",
        //F
        FIRST_NAME to "First Name",
        FILE_NO_FOUND to "No file found",
        //H
        HOME to "Home",
        //I
        INSTITUTION to "INSTITUTION",
        INSTRUCTOR to "Instructor Name",
        //L
        LAST_NAME to "Last Name",
        LEVEL  to "Level",
        LOGIN_FAILURE to "Login failure",
        LOADING to "Loading",
        LOGOUT to "Logout",
        LOGIN_HELP to "login help",
        LOGIN_TITLE to "LOGIN",
        //M
        MANAGE_QUIZZES to "Mange Quizzes",
        MANAGE_VIDEOS to "Manage Videos",
        MIDDLE_NAME to "Middle Name",
        MODULE_NAME to "Module Name",
        //N
        NAME_ON_CARD to "Name on Card",
        NEW_PASSWORD to "New Password",
        NO_GRADED_YET to "No graded yet",
        //O
        ORGANIZATION_NAME to "Organization Name",
        //P
        PASSWORD to "Password",
        PASSWORD_ERROR to "A password should contains a uppercase, a lowercase, 8 - 10 characters with at least one of these special characters: #, @, !",
        PAY_WITH_PAYPAL to "Pay with PayPal",
        PERSONAL_INFO to "Personal Information",
        PHONE to "Phone",
        PHONE_ERROR to "Your phone does not match this pattern {pattern}",
        PHONE_NUMBER to "Phone Number",
        //Q
        QUESTION to "Question",
        QUIZ_NUMBER to "Quiz Number",
        QUIZ to "Quiz",
        QUIZ_RESULT to "You scored {score} out of {total}",
        QUIZZES to "Quizzes",
        //R
        REGISTER to "Register",
        //S
        SELECTED to "SELECTED",
        SELECT_FILE to "Select File",
        SETTINGS to "Settings",
        SORRY_FOR_INCONVENIENCE to "Sorry for the inconvenience, we are unable to find the resource requested",
        SUBMIT_QUIZ to "SUBMIT QUIZ",
        SUBSCRIPTION_PAYMENT to "Subscription Payment",
        START_QUIZ to "START QUIZ",
        //U
        UPDATE_BUTTON to "Update",
        UPDATE_PICTURE to "Update Picture",
        USERNAME to "username",
        //V
        VALIDATE to "VALIDATE",
        VERIFICATION_CODE_KEY to "Verification Code",
        VERIFICATION_CODE_KEY to "VERIFICATION CODE",
        VERIFY to "VERIFY",
        VIDEOS to "Videos",
        VIDEO_TITLE to "Video Title",
        VIDEO_DESCRIPTION to "Video Description",
        VIDEO_NO_FOUND to "No Video Available",
        //W
        WELCOME to "Welcome Documentation Page." +
                "\nHere is where you can see you instructor course documentation" +
                "\nTo view a content, please select above.",
        WELCOME_TO_ASSESSMENT to "Welcome the assessments and Quizzes section." +
                " \nHere is where you can take a quiz and also see the result for a quiz taken." +
                "\nSelect the quiz you want to take or see the score in the above selection.",
        WRONG_CODE to "Please enter a valid code",
        //Y
        YOUR_ANSWER to "Your answer"
    )

    val fr = mapOf(
        //A
        ADD_DURATION to "Ajouter la durée de l'épreuve",
        ALREADY_TAKEN to "Déjà fait",
        ASSERTION to "option",
        ANSWER to "Réponse",
        AUTH_CODE to "Institution",
        //B
        BEFORE_YOU_TAKE to "Avant de commencer cette épreuve, assurez-vous d'avoir une connexion Internet stable\n" +
                "et une fois que vous cliquez sur continuer, vous ne pouvez pas quitter cet écran " +
                "ou le fermer avant de le soumettre.\n" +
                "Si vous ne respectez pas cela, vous obtiendrez la note la plus basse pour cette épreuve.",
        //C
        CARD_NUMBER to "Numéro de carte",
        CLOSE to "FERMER",
        CONFIRM_PASSWORD to "Confirmer Password",
        CONFIRM_NEW_PASSWORD to "Confirmer Nouveau Mot De Passe",
        CORRECT_ANSWER  to "Réponse correcte",
        COURSE_DESCRIPTION  to "Déscription",
        CVV  to "CVV",
        //D
        DOCUMENTATION  to "Documentation",
        DOCUMENTS  to "Documents",
        //E
        EMAIL to "Email",
        EMAIL_ERROR to "Votre email ne pas valide. Please enter un email avec ce format address@domain.extension",
        EMAIL_SAMPLE to "address@domain.extension",
        ENTER_PIN  to "ENTRER LE CODE",
        EXPIRATION_DATE  to "Date d'expiration (MM/AA)",
        //F
        FIRST_NAME to "Prénom",
        FILE_NO_FOUND to "Aucun document trouvé",
        //H
        HOME to "Accueil",
        //I
        INSTITUTION to "INSTITUTION",
        INSTRUCTOR to "Nom de l'enseignant",
        //L
        LAST_NAME to "Nome de Famille",
        LEVEL to "Niveau",
        LOGIN_FAILURE to "Échec de la connexion",
        LOADING to "En train téléchargé ...",
        LOGOUT to "Déconnexion",
        LOGIN_HELP to "Aide à la connexion",
        LOGIN_TITLE to "Se connecter",
        //M
        MANAGE_QUIZZES to "Gérer les Quiz",
        MANAGE_VIDEOS to "Gérer les Vidéos",
        MIDDLE_NAME to "Deuxième Prénom",
        MODULE_NAME to "Nom du Module",
        //N
        NAME_ON_CARD to "Nom sur la carte",
        NO_GRADED_YET to "Pas encore coté",
        //O
        ORGANIZATION_NAME to "Nom de l'institution",
        //P
        PASSWORD to "Mot de passe",
        PASSWORD_ERROR to "Le mot de passe doit avoir 8 - 10 alpha-numerique long avec au moins un de ces speciaux characters: #, @, !, et une lettre miniscule and une en majuscule",
        PAY_WITH_PAYPAL to "Payer avec PayPal",
        PERSONAL_INFO to "Informations personnelles",
        PHONE to "Téléphone",
        PHONE_ERROR to "Votre numéro ne ressemble pas à cet format {pattern}",
        PHONE_NUMBER to "Numéro De Téléphone",
        //Q
        QUESTION to "Question",
        QUIZ_NUMBER to "Epreuve numéro",
        QUIZ to "Epreuve",
        QUIZ_RESULT to "Votre note est {score} sur {total}",
        QUIZZES to "Interrogations",
        //R
        REGISTER to "S'inscrire",
        //S
        SELECTED to "SÉLECTIONNÉ",
        SELECT_FILE to "Sélectionner un fichier",
        SETTINGS to "Paramètres",
        SORRY_FOR_INCONVENIENCE to "Désolé pour le désagrément, nous ne pouvons pas trouver la ressource demandée.",
        SUBMIT_QUIZ to "SOUMETTRE L'EPREUVE POUR CORRECTION",
        SUBSCRIPTION_PAYMENT to "Paiement de l'abonnement",
        START_QUIZ to "COMMENCER L'EPREUVE",
        //U
        UPDATE_BUTTON to "Mettre à jour",
        UPDATE_PICTURE to "Changer l'image",
        USERNAME to "nom d'utilisateur",
        //V
        VALIDATE to "CONFIRMER",
        VERIFICATION_CODE_KEY to "code de vérification",
        VERIFICATION_CODE_TITLE to "CODE DE VERIFICATION",
        VERIFY to "CONFIRMER",
        VIDEOS to "Videos",
        VIDEO_DESCRIPTION to "Description",
        VIDEO_TITLE to "Titre de la vidéo",
        VIDEO_NO_FOUND to "Aucune vidéo disponible",
        //W
        WELCOME to "Bienvenue sur la page de documentation." +
                "\nC'est ici que vous pouvez voir la documentation des cours de votre instructeur." +
                "\nPour voir un contenu, veuillez sélectionner ci-dessus.",
        WELCOME_TO_ASSESSMENT to "Bienvenue dans la section des évaluations et des épreuves." +
                "\nC'est ici que vous pouvez passer une épreuve et voir le résultat d'une épreuve passée." +
                "\nSélectionnez l'épreuve que vous souhaitez passer ou consultez la note dans la sélection ci-dessus.",
        WRONG_CODE to "S'il vous plait, entrez un code valide",
        //Y
        YOUR_ANSWER to "Votre réponse"
    )

    var selectedlanguage = en

    fun get(key: String, placeHolders: Map<String, String> = emptyMap()): String {
        var template = selectedlanguage[key]?:""
        placeHolders.forEach{(placeHolder, value) ->
            template = template.replace("{$placeHolder}", value)
        }

        return template
    }

    fun setLanguage(language: LanguageOption) {
        when {
            language == LanguageOption.EN -> { selectedlanguage = en }
            language == LanguageOption.FR -> { selectedlanguage = fr }
            else -> selectedlanguage = en
        }
    }

    enum class LanguageOption {
        EN,
        FR
    }
}