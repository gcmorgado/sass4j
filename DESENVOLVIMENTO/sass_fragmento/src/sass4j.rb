#require_relative "src/java/sass"
require_relative $sassScript

def compile(sass)
    return Sass.compile(sass)
end
